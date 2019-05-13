package me.mqueiroz.picpay.ui.payment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import me.mqueiroz.picpay.common.entities.Card
import me.mqueiroz.picpay.common.entities.Receipt
import me.mqueiroz.picpay.common.entities.User
import me.mqueiroz.picpay.model.Repository
import me.mqueiroz.picpay.utils.schedulers.TrampolineSchedulerProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.*
import java.lang.RuntimeException
import java.text.DecimalFormat

@RunWith(MockitoJUnitRunner::class)
class PaymentViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mock<Repository>()

    private val schedulerProvider = TrampolineSchedulerProvider()

    private val decimalFormat = DecimalFormat()

    private val viewModel = PaymentViewModel(repository, schedulerProvider, decimalFormat)

    private val stateObserver = mock<Observer<PaymentFragmentState>>()

    @Before
    fun setUp() {
        viewModel.state.observeForever(stateObserver)
    }

    @Test
    fun setState_ShouldUpdatesUserAndCard() {
        // given
        val userObserver = mock<Observer<User>>()
        val cardObserver = mock<Observer<Card>>()
        viewModel.payee.observeForever(userObserver)
        viewModel.card.observeForever(cardObserver)

        val user = User("img", "name", 0, "username")
        val card = Card("name", "0000", 0, "date")

        // when
        viewModel.setState(user, card)

        // then
        verify(userObserver).onChanged(user)
        verify(cardObserver).onChanged(card)

        viewModel.payee.removeObserver(userObserver)
        viewModel.card.removeObserver(cardObserver)
    }

    @Test
    fun onValueChanged_ShouldEnablePayment_WhenValueIsValidAndGreaterThan0() {
        // given
        val input = decimalFormat.format(10.00).toString()

        // when
        viewModel.onValueChanged(input)

        // then
        verify(stateObserver).onChanged(PaymentFragmentState.PaymentEnabled)
    }

    @Test
    fun onValueChanged_ShouldDisablePayment_WhenValueIsInvalid() {
        // given
        val input = "abc"

        // when
        viewModel.onValueChanged(input)

        // then
        verify(stateObserver).onChanged(PaymentFragmentState.PaymentDisabled)
    }

    @Test
    fun onValueChanged_ShouldDisablePayment_WhenValueIsEqualTo0() {
        // given
        val input = decimalFormat.format(0.00).toString()

        // when
        viewModel.onValueChanged(input)

        // then
        verify(stateObserver).onChanged(PaymentFragmentState.PaymentDisabled)
    }

    @Test
    fun onValueChanged_ShouldDisablePayment_WhenValueIsSmallerThan0() {
        // given
        val input = decimalFormat.format(-1.00).toString()

        // when
        viewModel.onValueChanged(input)

        // then
        verify(stateObserver).onChanged(PaymentFragmentState.PaymentDisabled)
    }

    @Test
    fun onClickPay_ShouldSetSuccessState_onSuccess() {
        // given
        val user = User("img", "name", 0, "username")
        val card = Card("name", "0000", 0, "date")
        viewModel.setState(user, card)

        val receipt = Receipt(user, true, 0, 20.00, 1234, "SUCCESS")
        `when`(repository.saveTransaction(anyOrNull())).thenReturn(Single.just(receipt))

        // when
        viewModel.onClickPay()

        // then
        inOrder(stateObserver) {
            verify(stateObserver).onChanged(PaymentFragmentState.ProcessingPayment)
            verify(stateObserver).onChanged(PaymentFragmentState.PaymentSuccess(receipt))
            verifyNoMoreInteractions(stateObserver)
        }
    }

    @Test
    fun onClickPay_ShouldSetSuccessState_onError() {
        // given
        val user = User("img", "name", 0, "username")
        val card = Card("name", "0000", 0, "date")
        viewModel.setState(user, card)

        val message = "Teste"
        val exception = RuntimeException(message)
        `when`(repository.saveTransaction(anyOrNull())).thenReturn(Single.error(exception))

        // when
        viewModel.onClickPay()

        // then
        inOrder(stateObserver) {
            verify(stateObserver).onChanged(PaymentFragmentState.ProcessingPayment)
            verify(stateObserver).onChanged(PaymentFragmentState.PaymentError())
            verifyNoMoreInteractions(stateObserver)
        }
    }

    @After
    fun tearDown() {
        viewModel.state.removeObserver(stateObserver)
    }
}
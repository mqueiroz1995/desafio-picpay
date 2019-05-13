package me.mqueiroz.picpay.ui.card.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Completable
import me.mqueiroz.picpay.model.Repository
import me.mqueiroz.picpay.utils.schedulers.TrampolineSchedulerProvider
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.*
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class CardRegisterViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mock<Repository>()

    private val schedulerProvider = TrampolineSchedulerProvider()

    private val viewModel = CardRegisterViewModel(repository, schedulerProvider)

    @Test
    fun onClickSave_ShouldSetSuccessState_onComplete() {
        // given
        viewModel.onCardNumberChanged("0000")
        viewModel.onNameChanged("Name")
        viewModel.onExpiryDateChanged("01/21")
        viewModel.onCVVChanged("000")
        `when`(repository.saveCard(anyOrNull())).thenReturn(Completable.complete())

        val stateObserver = mock<Observer<CardRegisterFragmentState>>()
        viewModel.state.observeForever(stateObserver)

        // when
        viewModel.onClickSave()

        // then
        verify(stateObserver).onChanged(CardRegisterFragmentState.Success(any()))
        viewModel.state.removeObserver(stateObserver)
    }

    @Test
    fun onClickSave_ShouldSetErrorState_onError() {
        // given
        viewModel.onCardNumberChanged("0000")
        viewModel.onNameChanged("Name")
        viewModel.onExpiryDateChanged("01/21")
        viewModel.onCVVChanged("000")

        val stateObserver = mock<Observer<CardRegisterFragmentState>>()
        viewModel.state.observeForever(stateObserver)

        val exception = RuntimeException("teste")
        `when`(repository.saveCard(anyOrNull())).thenReturn(Completable.error(exception))

        // when
        viewModel.onClickSave()

        // then
        verify(stateObserver).onChanged(CardRegisterFragmentState.Error)
        viewModel.state.removeObserver(stateObserver)
    }
}
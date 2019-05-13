package me.mqueiroz.picpay.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import me.mqueiroz.picpay.common.entities.Card
import me.mqueiroz.picpay.common.entities.Transaction
import me.mqueiroz.picpay.common.entities.User
import me.mqueiroz.picpay.model.Repository
import me.mqueiroz.picpay.utils.schedulers.SchedulerProvider
import java.text.DecimalFormat
import javax.inject.Inject

class PaymentViewModel @Inject constructor(
        private val repository: Repository,
        private val schedulerProvider: SchedulerProvider,
        private val decimalFormat: DecimalFormat
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val mPayee = MutableLiveData<User>()
    private val mCard = MutableLiveData<Card>()

    private val mState = MutableLiveData<PaymentFragmentState>()

    private var value = 0.00

    val payee: LiveData<User> = mPayee
    val card: LiveData<Card> = mCard

    val state: LiveData<PaymentFragmentState> = mState

    fun setState(user: User, card: Card) {
        this.mPayee.value = user
        this.mCard.value = card
    }

    fun onValueChanged(string: String) {
        try {
            val number = decimalFormat.parse(string)

            value = number.toDouble()
            mState.value =
                    if (this.value > 0) PaymentFragmentState.PaymentEnabled
                    else PaymentFragmentState.PaymentDisabled
        } catch (e: Exception) {
            mState.value = PaymentFragmentState.PaymentDisabled
        }
    }

    fun onClickPay() {
        val transaction = Transaction(mPayee.value!!, mCard.value!!, 20.00)
        repository.saveTransaction(transaction)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { mState.value = PaymentFragmentState.ProcessingPayment }
                .subscribe(
                        { receipt ->
//                            if (receipt.success) {
                                mState.value = PaymentFragmentState.PaymentSuccess(receipt)
//                            } else {
                                // TODO:
//                            }
                        },
                        { mState.value = PaymentFragmentState.PaymentError() })
                .addTo(disposable)
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}
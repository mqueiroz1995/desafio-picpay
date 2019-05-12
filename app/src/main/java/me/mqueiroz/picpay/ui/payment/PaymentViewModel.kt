package me.mqueiroz.picpay.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.mqueiroz.picpay.common.entities.Card
import me.mqueiroz.picpay.common.entities.User
import me.mqueiroz.picpay.model.Repository
import me.mqueiroz.picpay.utils.StringResource
import me.mqueiroz.picpay.utils.schedulers.SchedulerProvider
import javax.inject.Inject

class PaymentViewModel @Inject constructor(
        private val repository: Repository,
        private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val mPayee = MutableLiveData<User>()
    private val mCard = MutableLiveData<Card>()
    private val mCurrency = MutableLiveData<StringResource>()

    val payee: LiveData<User> = mPayee
    val card: LiveData<Card> = mCard
    val currency: LiveData<StringResource> = mCurrency

    fun setState(user: User, card: Card) {
        this.mPayee.value = user
        this.mCard.value = card
    }


}
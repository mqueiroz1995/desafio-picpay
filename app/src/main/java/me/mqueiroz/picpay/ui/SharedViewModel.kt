package me.mqueiroz.picpay.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.mqueiroz.picpay.common.entities.Card
import me.mqueiroz.picpay.common.entities.User
import javax.inject.Inject

class SharedViewModel @Inject constructor() : ViewModel() {

    private val mPayee = MutableLiveData<User>()
    private val mCard = MutableLiveData<Card>()

    val payee: LiveData<User> = mPayee
    val card: LiveData<Card> = mCard

    init {
        mPayee.value = User(
            "http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png",
            "Matheus",
            1021,
            "mqueiroz"
        )
        mCard.value = Card("TESTE", "21312321313", 123, "05/11")
    }
}
package me.mqueiroz.picpay.ui.card.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import me.mqueiroz.picpay.common.entities.Card
import me.mqueiroz.picpay.model.Repository
import me.mqueiroz.picpay.utils.schedulers.SchedulerProvider
import java.util.regex.Pattern
import javax.inject.Inject

class CardRegisterViewModel @Inject constructor(
        private val repository: Repository,
        private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val mState = MutableLiveData<CardRegisterFragmentState>()

    private var isCardNumberValid = false
    private var isNameValid = false
    private var isExpiryDateValid = false
    private var isCVVValid = false

    private var cardNumber = ""
    private var name = ""
    private var expiryDate = ""
    private var cvv = ""

    val state: LiveData<CardRegisterFragmentState> = mState

    fun onCardNumberChanged(number: String) {
        cardNumber = number
        isCardNumberValid = Pattern.matches("^[0-9]*\$", number) && number.length == 16

        refreshState()
    }

    fun onNameChanged(name: String) {
        this.name = name
        isNameValid = true
        refreshState()
    }

    fun onExpiryDateChanged(date: String) {
        try {
            expiryDate = date
            if (date.length == 5) {
                val components = date.split("/")
                val month = components[0].toInt()
                val year = components[1].toInt()
                isExpiryDateValid = (month in 1..12) && (year > 0)
            } else {
                isExpiryDateValid = false
            }
        } catch (ex: Exception) {
            isExpiryDateValid = false
        } finally {
            refreshState()
        }
    }

    fun onCVVChanged(cvv: String) {
        this.cvv = cvv
        this.isCVVValid = Pattern.matches("^[0-9]*\$", cvv) && cvv.length == 3
        refreshState()
    }

    fun onClickSave() {
        val card = Card(name, cardNumber, cvv.toInt(), expiryDate)
        repository.saveCard(card)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ mState.value = CardRegisterFragmentState.Success(card) },
                        { mState.value = CardRegisterFragmentState.Error }
                )
                .addTo(disposable)
    }

    private fun refreshState() {
        if (isCardNumberValid && isNameValid && isExpiryDateValid && isCVVValid) {
            mState.value = CardRegisterFragmentState.SaveEnabled
        } else {
            mState.value = CardRegisterFragmentState.SaveDisabled
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}
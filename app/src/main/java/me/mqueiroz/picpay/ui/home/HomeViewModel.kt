package me.mqueiroz.picpay.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import me.mqueiroz.picpay.common.entities.User
import me.mqueiroz.picpay.model.Repository
import me.mqueiroz.picpay.utils.SingleLiveEvent
import me.mqueiroz.picpay.utils.schedulers.SchedulerProvider
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val repository: Repository,
        private val schedulerProvider: SchedulerProvider
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val mState = MutableLiveData<HomeFragmentState>()

    val state: LiveData<HomeFragmentState> = mState

    val navigate = SingleLiveEvent<HomeFragmentNavigation>()

    fun loadUsers() {
        repository.getUsers()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { mState.value = HomeFragmentState.Loading }
                .subscribe(
                        { mState.value = HomeFragmentState.Loaded(it) },
                        { mState.value = HomeFragmentState.Error() })
                .addTo(disposable)
    }

    fun onUserSelected(user: User) {
        repository.getCard()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnComplete { navigate.value = HomeFragmentNavigation.CardPrimingScreen(user) }
                .subscribe(
                        { card -> navigate.value = HomeFragmentNavigation.PaymentScreen(user, card) },
                        { mState.value = HomeFragmentState.Error() }
                )
                .addTo(disposable)
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}

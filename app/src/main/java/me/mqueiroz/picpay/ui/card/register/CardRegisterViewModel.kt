package me.mqueiroz.picpay.ui.card.register

import androidx.lifecycle.ViewModel
import me.mqueiroz.picpay.model.Repository
import me.mqueiroz.picpay.utils.schedulers.SchedulerProvider
import javax.inject.Inject

class CardRegisterViewModel @Inject constructor(
    private val repository: Repository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {


}
package me.mqueiroz.picpay.utils.schedulers

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RuntimeSchedulerProvider : SchedulerProvider {
    override fun computation() = Schedulers.computation()
    override fun io() = Schedulers.io()
    override fun ui() = AndroidSchedulers.mainThread()
}
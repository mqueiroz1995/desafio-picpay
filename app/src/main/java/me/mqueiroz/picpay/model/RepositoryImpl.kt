package me.mqueiroz.picpay.model

import io.reactivex.Observable
import me.mqueiroz.picpay.common.entities.User
import me.mqueiroz.picpay.model.network.PicPayService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val picPayService: PicPayService
) : Repository {

    override fun getUsers(): Observable<List<User>> {
        return picPayService.getUsers().toObservable()
    }
}
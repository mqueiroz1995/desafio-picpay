package me.mqueiroz.picpay.model

import io.reactivex.Observable
import me.mqueiroz.picpay.common.entities.User

interface Repository {

    fun getUsers(): Observable<List<User>>
}
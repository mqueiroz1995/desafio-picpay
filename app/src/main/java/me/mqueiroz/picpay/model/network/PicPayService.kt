package me.mqueiroz.picpay.model.network

import io.reactivex.Single
import me.mqueiroz.picpay.common.entities.User
import retrofit2.http.GET


interface PicPayService {

    @GET("users")
    fun getUsers() : Single<List<User>>
}
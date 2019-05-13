package me.mqueiroz.picpay.model.network

import io.reactivex.Single
import me.mqueiroz.picpay.common.entities.Receipt
import me.mqueiroz.picpay.common.entities.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface PicPayService {

    @GET("users")
    fun getUsers(): Single<List<User>>

    @POST("transaction")
    fun postTransaction(@Body body: PostTransactionRequestBody): Single<PostTransactionResponse>
}
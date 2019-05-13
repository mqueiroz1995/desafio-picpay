package me.mqueiroz.picpay.common.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Transaction(

    @Json(name = "payee")
    val payee: User,

    @Json(name = "card")
    val card: Card,

    @Json(name = "value")
    val value: Double
)
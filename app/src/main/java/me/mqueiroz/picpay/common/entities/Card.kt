package me.mqueiroz.picpay.common.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Card(

    @Json(name = "name")
    val name: String,

    @Json(name = "card_number")
    val number: String,

    @Json(name = "cvv")
    val cvv: Int,

    @Json(name = "expiryDate")
    val expiryDate: String
)
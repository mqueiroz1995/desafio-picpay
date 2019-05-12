package me.mqueiroz.picpay.common.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Card(

        @Json(name = "cvv")
        val cvv: Int,

        @Json(name = "card_number")
        val cardNumber: String,

        @Json(name = "expiry_date")
        val expiryDate: String,

        @Json(name = "value")
        val value: Double
)
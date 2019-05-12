package me.mqueiroz.picpay.common.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Card(

        @Json(name = "cvv")
        val cvv: Int? = null,

        @Json(name = "card_number")
        val cardNumber: String? = null,

        @Json(name = "expiry_date")
        val expiryDate: String? = null,

        @Json(name = "value")
        val value: Double? = null
)
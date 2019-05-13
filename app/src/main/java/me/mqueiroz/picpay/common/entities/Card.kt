package me.mqueiroz.picpay.common.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable
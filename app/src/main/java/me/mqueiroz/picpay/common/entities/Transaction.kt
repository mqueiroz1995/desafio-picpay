package me.mqueiroz.picpay.common.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Transaction(

    @Json(name = "payee")
    val payee: User,

    @Json(name = "card")
    val card: Card,

    @Json(name = "value")
    val value: Double
) : Parcelable
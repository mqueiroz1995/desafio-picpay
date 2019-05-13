package me.mqueiroz.picpay.common.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Receipt(

    @Json(name = "destination_user")
    val destinationUser: User,

    @Json(name = "success")
    val success: Boolean,

    @Json(name = "id")
    val id: Int,

    @Json(name = "value")
    val value: Double,

    @Json(name = "timestamp")
    val timestamp: Int,

    @Json(name = "status")
    val status: String
) : Parcelable
package me.mqueiroz.picpay.common.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(

        @Json(name = "img")
        val img: String,

        @Json(name = "name")
        val name: String,

        @Json(name = "id")
        val id: Int,

        @Json(name = "username")
        val username: String
)
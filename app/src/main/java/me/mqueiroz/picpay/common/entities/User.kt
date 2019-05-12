package me.mqueiroz.picpay.common.entities

import com.squareup.moshi.Json

data class User(
    @Json(name = "img")
    val img: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "username")
    val username: String? = null
)
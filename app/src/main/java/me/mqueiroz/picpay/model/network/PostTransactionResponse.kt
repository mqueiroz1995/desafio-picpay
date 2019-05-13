package me.mqueiroz.picpay.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import me.mqueiroz.picpay.common.entities.Receipt

@JsonClass(generateAdapter = true)
data class PostTransactionResponse(

    @Json(name = "transaction")
    val receipt: Receipt
)
package me.mqueiroz.picpay.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import me.mqueiroz.picpay.common.entities.Transaction

@JsonClass(generateAdapter = true)
data class PostTransactionRequestBody(
    @Json(name = "destination_user_id")
    val payee: Int,

    @Json(name = "card_number")
    val cardNumber: String,

    @Json(name = "cvv")
    val cvv: Int,

    @Json(name = "expiry_date")
    val expiryDate: String,

    @Json(name = "value")
    val value: Double
) {

    companion object {

        fun fromTransaction(transaction: Transaction): PostTransactionRequestBody {
            val payee = transaction.payee.id
            val cardNumber = transaction.card.number
            val cardCvv = transaction.card.cvv
            val cardExpiryDate = transaction.card.expiryDate
            val value = transaction.value

            return PostTransactionRequestBody(payee, cardNumber, cardCvv, cardExpiryDate, value)
        }
    }
}
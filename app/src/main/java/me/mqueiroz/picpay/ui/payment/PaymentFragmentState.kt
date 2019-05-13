package me.mqueiroz.picpay.ui.payment

import me.mqueiroz.picpay.R
import me.mqueiroz.picpay.common.entities.Receipt
import me.mqueiroz.picpay.utils.StringResource

sealed class PaymentFragmentState {

    object PaymentDisabled : PaymentFragmentState()
    object PaymentEnabled : PaymentFragmentState()
    object ProcessingPayment : PaymentFragmentState()
    data class PaymentSuccess(val receipt: Receipt) : PaymentFragmentState()
    data class PaymentError(val message: StringResource = StringResource.defaultError()) : PaymentFragmentState()
}
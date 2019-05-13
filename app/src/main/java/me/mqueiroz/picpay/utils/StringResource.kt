package me.mqueiroz.picpay.utils

import me.mqueiroz.picpay.R

data class StringResource(
        val id: Int
) {
    companion object {
        @JvmStatic
        fun defaultError(): StringResource = StringResource(R.string.error)
    }
}
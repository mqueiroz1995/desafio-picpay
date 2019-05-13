package me.mqueiroz.picpay.ui.home

import me.mqueiroz.picpay.common.entities.Card
import me.mqueiroz.picpay.common.entities.User

sealed class HomeFragmentNavigation {

    data class CardPrimingScreen(val user: User) : HomeFragmentNavigation()
    data class PaymentScreen(val user: User, val card: Card) : HomeFragmentNavigation()
}
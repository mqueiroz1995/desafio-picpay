package me.mqueiroz.picpay.ui.card.register

import me.mqueiroz.picpay.common.entities.Card

sealed class CardRegisterFragmentState {

    object SaveDisabled : CardRegisterFragmentState()
    object SaveEnabled : CardRegisterFragmentState()
    data class Success(val card: Card) : CardRegisterFragmentState()
    object Error : CardRegisterFragmentState()
}
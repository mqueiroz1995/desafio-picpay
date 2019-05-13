package me.mqueiroz.picpay.ui.card.register

sealed class CardRegisterFragmentState {

    object SaveDisabled : CardRegisterFragmentState()
    object SaveEnabled : CardRegisterFragmentState()
    object Success : CardRegisterFragmentState()
    object Error : CardRegisterFragmentState()
}
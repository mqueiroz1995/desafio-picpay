package me.mqueiroz.picpay.ui.home

import me.mqueiroz.picpay.common.entities.User
import me.mqueiroz.picpay.utils.StringResource

sealed class HomeFragmentState {
    object Loading : HomeFragmentState()
    data class Error(val message: StringResource = StringResource.defaultError()) : HomeFragmentState()
    data class Loaded(val users: List<User>) : HomeFragmentState()
}
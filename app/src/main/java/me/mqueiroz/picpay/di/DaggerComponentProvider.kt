package me.mqueiroz.picpay.di

import androidx.fragment.app.Fragment

interface DaggerComponentProvider {

    val component: ApplicationComponent
}

val Fragment.injector get() = (context!!.applicationContext as DaggerComponentProvider).component

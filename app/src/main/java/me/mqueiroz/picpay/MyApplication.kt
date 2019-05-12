package me.mqueiroz.picpay

import android.app.Application
import me.mqueiroz.picpay.di.components.ApplicationComponent
import me.mqueiroz.picpay.di.DaggerComponentProvider
import me.mqueiroz.picpay.di.components.DaggerApplicationComponent

class MyApplication : Application(), DaggerComponentProvider {

    override val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationContext(applicationContext)
            .build()
    }
}
package me.mqueiroz.picpay

import android.app.Application
import me.mqueiroz.picpay.di.ApplicationComponent
import me.mqueiroz.picpay.di.DaggerApplicationComponent
import me.mqueiroz.picpay.di.DaggerComponentProvider

class MyApplication : Application(), DaggerComponentProvider {

    override val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationContext(applicationContext)
            .build()
    }
}
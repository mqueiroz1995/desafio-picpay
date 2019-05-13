package me.mqueiroz.picpay.di.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides


@Module
class AppModule {
    private val SHARED_PREFS = "shared_prefs"

    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    }
}
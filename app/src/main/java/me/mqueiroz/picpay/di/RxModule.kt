package me.mqueiroz.picpay.di

import dagger.Module
import dagger.Provides
import me.mqueiroz.picpay.utils.schedulers.RuntimeSchedulerProvider
import me.mqueiroz.picpay.utils.schedulers.SchedulerProvider

@Module
object RxModule {

    @Provides
    @JvmStatic
    fun providesSchedulerProvider(): SchedulerProvider {
        return RuntimeSchedulerProvider()
    }
}
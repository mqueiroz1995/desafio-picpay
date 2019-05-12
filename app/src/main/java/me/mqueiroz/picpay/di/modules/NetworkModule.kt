package me.mqueiroz.picpay.di.modules

import dagger.Module
import dagger.Provides
import me.mqueiroz.picpay.model.network.PicPayService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    private val BASE_URL = "http://careers.picpay.com/tests/mobdev/"

    @Provides
    fun providesPicPayService(): PicPayService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PicPayService::class.java)
    }
}
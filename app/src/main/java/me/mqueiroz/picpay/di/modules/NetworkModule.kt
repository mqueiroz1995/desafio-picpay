package me.mqueiroz.picpay.di.modules

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import me.mqueiroz.picpay.model.network.PicPayService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient


@Module
object NetworkModule {

    @JvmStatic
    private val BASE_URL = "http://careers.picpay.com/tests/mobdev/"

    @JvmStatic
    @Provides
    @Singleton
    fun providesPicPayService(client: OkHttpClient, moshi: Moshi): PicPayService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(PicPayService::class.java)
    }

    @JvmStatic
    @Provides
    @Singleton
    fun providesOkHTTP(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }
}
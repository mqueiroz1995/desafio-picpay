package me.mqueiroz.picpay.di.modules

import dagger.Module
import dagger.Binds
import me.mqueiroz.picpay.model.Repository
import me.mqueiroz.picpay.model.RepositoryImpl
import javax.inject.Singleton


@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(repository: RepositoryImpl): Repository
}
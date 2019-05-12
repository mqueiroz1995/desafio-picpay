package me.mqueiroz.picpay.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import me.mqueiroz.picpay.ui.home.HomeViewModel
import me.mqueiroz.picpay.utils.ViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepositoryModule::class,
        RxModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): ApplicationComponent
    }

    fun homeViewModelFactory(): ViewModelFactory<HomeViewModel>
}

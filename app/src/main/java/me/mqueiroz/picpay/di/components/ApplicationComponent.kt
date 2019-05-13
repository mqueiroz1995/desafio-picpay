package me.mqueiroz.picpay.di.components

import android.content.Context
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import me.mqueiroz.picpay.di.modules.*
import me.mqueiroz.picpay.ui.SharedViewModel
import me.mqueiroz.picpay.ui.card.register.CardRegisterViewModel
import me.mqueiroz.picpay.ui.home.HomeViewModel
import me.mqueiroz.picpay.ui.payment.PaymentViewModel
import me.mqueiroz.picpay.utils.ViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            RepositoryModule::class,
            NetworkModule::class,
            RxModule::class,
            UtilsModule::class
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

    fun cardRegisterViewModelFactory(): ViewModelFactory<CardRegisterViewModel>

    fun paymentViewModelFactory(): ViewModelFactory<PaymentViewModel>

    fun sharedViewModelFactory(): ViewModelFactory<SharedViewModel>
}

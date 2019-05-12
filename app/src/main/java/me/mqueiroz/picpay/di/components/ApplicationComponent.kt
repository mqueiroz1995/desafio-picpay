package me.mqueiroz.picpay.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import me.mqueiroz.picpay.di.modules.NetworkModule
import me.mqueiroz.picpay.di.modules.RepositoryModule
import me.mqueiroz.picpay.di.modules.RxModule
import me.mqueiroz.picpay.ui.card.register.CardRegisterViewModel
import me.mqueiroz.picpay.ui.home.HomeViewModel
import me.mqueiroz.picpay.ui.payment.PaymentViewModel
import me.mqueiroz.picpay.utils.ViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepositoryModule::class,
        NetworkModule::class,
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

    fun cardRegisterViewModelFactory(): ViewModelFactory<CardRegisterViewModel>

    fun paymentViewModelFactory(): ViewModelFactory<PaymentViewModel>
}

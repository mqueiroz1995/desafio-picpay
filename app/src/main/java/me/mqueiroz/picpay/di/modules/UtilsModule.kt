package me.mqueiroz.picpay.di.modules

import dagger.Module
import dagger.Provides
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.inject.Singleton

@Module
object UtilsModule {

    @JvmStatic
    @Provides
    @Singleton
    fun providesDecimalFormat(): DecimalFormat {
        val formatString = "#,###,###,##0.00"
        val currentLocale = Locale.getDefault()
        val otherSymbols = DecimalFormatSymbols(currentLocale)
        otherSymbols.decimalSeparator = ','
        otherSymbols.groupingSeparator = '.'
        return DecimalFormat(formatString, otherSymbols)
    }
}
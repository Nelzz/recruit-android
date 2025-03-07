package nz.co.test.transactions.di

import dagger.Module
import dagger.Provides
import nz.co.test.transactions.utils.Calculator
import nz.co.test.transactions.utils.GstCalculator

@Module
object CalculatorModule {

    @Provides
    fun provideCalculator(): Calculator {
        return GstCalculator()
    }
}
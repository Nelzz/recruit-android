package nz.co.test.transactions.di

import dagger.Component
import nz.co.test.transactions.activities.MainActivity
import nz.co.test.transactions.di.activities.ActivitiesModule
import nz.co.test.transactions.di.network.NetworkModule
import nz.co.test.transactions.di.network.TransactionServiceModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        ActivitiesModule::class,
        CalculatorModule::class,
        RepositoryModule::class,
        TransactionServiceModule::class,
        ViewModelModule::class,
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(appComponent: DaggerAppComponentFactory)
    fun inject(activity: MainActivity)
}
package nz.co.test.transactions

import android.app.Application
import nz.co.test.transactions.di.AppComponent
import nz.co.test.transactions.di.AppModule
import nz.co.test.transactions.di.DaggerAppComponent
import nz.co.test.transactions.di.DaggerAppComponentFactory

class MainApp : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        // I had to manually inject this.
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(DaggerAppComponentFactory())

    }
}
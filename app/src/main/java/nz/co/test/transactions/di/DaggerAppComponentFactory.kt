package nz.co.test.transactions.di

import android.app.Activity
import android.app.Application
import androidx.core.app.AppComponentFactory
import javax.inject.Inject
import javax.inject.Provider

class DaggerAppComponentFactory : AppComponentFactory() {

    @Inject
    lateinit var map: Map<Class<out Activity>, @JvmSuppressWildcards Provider<Activity>>
}
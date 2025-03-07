package nz.co.test.transactions.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import nz.co.test.transactions.activities.mappers.UiModelMapper
import nz.co.test.transactions.repositories.TransactionRepository
import nz.co.test.transactions.viewmodels.MainViewModel
import nz.co.test.transactions.viewmodels.ViewModelFactory
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
object ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun provideMainViewModel(repository: TransactionRepository, mapper: UiModelMapper): ViewModel {
        return MainViewModel(repository, mapper)
    }

    @Provides
    fun provideViewModelFactory(
        viewModels: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return ViewModelFactory(viewModels)
    }
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
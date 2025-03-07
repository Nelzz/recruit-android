package nz.co.test.transactions.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import nz.co.test.transactions.repositories.TransactionRepository
import nz.co.test.transactions.repositories.TransactionRepositoryImp
import nz.co.test.transactions.services.TransactionsService
import javax.inject.Singleton

@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(apiService: TransactionsService): TransactionRepository {
        return TransactionRepositoryImp(apiService)
    }
}
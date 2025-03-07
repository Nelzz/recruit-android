package nz.co.test.transactions.di.network

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import nz.co.test.transactions.services.MockTransactionService
import nz.co.test.transactions.services.TransactionsService

@Module
object TransactionServiceModule {

    @Provides
    fun provideMockService(context: Context, moshi: Moshi): TransactionsService {
        return MockTransactionService(context, moshi)
    }
}
package nz.co.test.transactions.repositories

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import nz.co.test.transactions.services.Transaction
import nz.co.test.transactions.services.TransactionsService
import javax.inject.Inject

interface TransactionRepository {
    suspend fun getTransactions()
    val transactions: Flow<Result<List<Transaction>>>
}

class TransactionRepositoryImp @Inject constructor(
    private val transactionsService: TransactionsService
) : TransactionRepository {

    private val _transactions = MutableSharedFlow<Result<List<Transaction>>>()

    override suspend fun getTransactions() {
        try {
            delay(1000) // mimic network call
            val results =  transactionsService.retrieveTransactions().toList()
            _transactions.emit(Result.success(results))
        } catch (e: Exception) {
            _transactions.emit(Result.failure(e))
        }
    }

    override val transactions: Flow<Result<List<Transaction>>>
        get() = _transactions.asSharedFlow()

}
package nz.co.test.transactions.services

import android.content.Context
import com.squareup.moshi.Moshi
import retrofit2.http.GET
import javax.inject.Inject

interface TransactionsService {
    @GET("transactions")
    suspend fun retrieveTransactions(): Array<Transaction>
}


class MockTransactionService @Inject constructor(
    private val context: Context,
    private val moshi: Moshi
): TransactionsService {
    override suspend fun retrieveTransactions(): Array<Transaction> {
        val jsonString = context.assets.open("transactions.json").bufferedReader().use { it.readText() }

        val adapter = moshi.adapter(Array<Transaction>::class.java)
        return adapter.fromJson(jsonString) ?: emptyArray()
    }

}


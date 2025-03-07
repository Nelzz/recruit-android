package nz.co.test.transactions.services

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
data class Transaction(
    @Json(name = "id") val id: Int,
    @Json(name = "transactionDate") val transactionDate: LocalDateTime,
    @Json(name = "summary") val summary: String,
    @Json(name = "debit") val debit: BigDecimal,
    @Json(name = "credit") val credit: BigDecimal
)
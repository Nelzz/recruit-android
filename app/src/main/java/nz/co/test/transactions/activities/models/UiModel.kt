package nz.co.test.transactions.activities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.time.LocalDateTime

@Parcelize
data class UiModel(
    val id: Int,
    val transactionDate: LocalDateTime,
    val summary: String,
    val debit: BigDecimal,
    val credit: BigDecimal,
    val gst: BigDecimal
) : Parcelable
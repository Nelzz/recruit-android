package nz.co.test.transactions.activities.mappers

import nz.co.test.transactions.activities.models.UiModel
import nz.co.test.transactions.services.Transaction
import nz.co.test.transactions.utils.Calculator
import java.math.BigDecimal
import javax.inject.Inject

class UiModelMapper @Inject constructor(
    private val calculator: Calculator
) {
    fun from(input: Transaction) = with(input) {
        UiModel(
            id = this.id,
            summary = this.summary,
            transactionDate = this.transactionDate,
            debit = this.debit,
            credit = this.credit,
            gst = calculator.calculate(if (debit.compareTo(BigDecimal.ZERO) == 0) credit else debit)
        )
    }
}
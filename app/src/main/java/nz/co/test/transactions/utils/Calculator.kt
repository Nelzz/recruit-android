package nz.co.test.transactions.utils

import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

interface Calculator {
    fun calculate(amount: BigDecimal): BigDecimal
}

class GstCalculator @Inject constructor(): Calculator {
    override fun calculate(amount: BigDecimal): BigDecimal {
        return amount
            .multiply(GST_RATE)
            .divide(GST_PERCENTAGE,RoundingMode.HALF_UP)
            .setScale(2, RoundingMode.HALF_UP)
    }

    companion object {
        private val GST_RATE = BigDecimal("0.15")  // 15% GST
        private val GST_PERCENTAGE = BigDecimal("1.15")  // 115% (Base Price + GST)
    }

}
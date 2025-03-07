package nz.co.test.transactions

import nz.co.test.transactions.utils.GstCalculator
import java.math.BigDecimal
import org.junit.Assert.assertEquals
import org.junit.Test

class GstCalculatorTest {

    private val calculator = GstCalculator()

    @Test
    fun `calculate should return correct GST amount`() {
        // Test Cases: Expected GST values
        val testCases = listOf(
            BigDecimal("115.00") to BigDecimal("15.00"), // 15% GST on total $115
            BigDecimal("230.00") to BigDecimal("30.00"), // 15% GST on total $230
            BigDecimal("345.00") to BigDecimal("45.00"), // 15% GST on total $345
            BigDecimal("0.00") to BigDecimal("0.00") // Edge case: 0 input
        )

        for ((amount, expectedGst) in testCases) {
            val result = calculator.calculate(amount)
            assertEquals("Incorrect GST for $amount", expectedGst, result)
        }
    }

    @Test
    fun `calculate should round correctly`() {
        val amount = BigDecimal("100.567")
        val expectedGst = BigDecimal("13.12")

        val result = calculator.calculate(amount)
        assertEquals("Rounding error", expectedGst, result)
    }

    @Test
    fun `calculate should handle large values`() {
        val amount = BigDecimal("1000000.00")
        val expectedGst = BigDecimal("130434.78")

        val result = calculator.calculate(amount)
        assertEquals("Incorrect GST for large amount", expectedGst, result)
    }
}
package nz.co.test.transactions

import io.mockk.every
import io.mockk.mockk
import nz.co.test.transactions.activities.mappers.UiModelMapper
import nz.co.test.transactions.services.Transaction
import nz.co.test.transactions.utils.Calculator
import java.math.BigDecimal
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class UiModelMapperTest {

    private val calculator: Calculator = mockk()
    private val mapper = UiModelMapper(calculator)

    @Test
    fun `should map Transaction to UiModel correctly when debit is non-zero`() {
        // Given
        val transaction = Transaction(
            id = 1,
            transactionDate = LocalDateTime.now(),
            summary = "Test Transaction",
            debit = BigDecimal("100.00"),
            credit = BigDecimal.ZERO
        )
        val expectedGst = BigDecimal("15.00")

        every { calculator.calculate(transaction.debit) } returns expectedGst

        // When
        val result = mapper.from(transaction)

        // Then
        assertEquals(transaction.summary, result.summary)
        assertEquals(transaction.transactionDate, result.transactionDate)
        assertEquals(transaction.debit, result.debit)
        assertEquals(transaction.credit, result.credit)
        assertEquals(expectedGst, result.gst)
    }

    @Test
    fun `should map Transaction to UiModel correctly when credit is non-zero`() {
        // Given
        val transaction = Transaction(
            id = 2,
            transactionDate = LocalDateTime.now(),
            summary = "Test Credit Transaction",
            debit = BigDecimal.ZERO,
            credit = BigDecimal("200.00")
        )
        val expectedGst = BigDecimal("30.00")

        every { calculator.calculate(transaction.credit) } returns expectedGst

        // When
        val result = mapper.from(transaction)

        // Then
        assertEquals(transaction.summary, result.summary)
        assertEquals(transaction.transactionDate, result.transactionDate)
        assertEquals(transaction.debit, result.debit)
        assertEquals(transaction.credit, result.credit)
        assertEquals(expectedGst, result.gst)
    }

    @Test
    fun `should handle zero debit and credit gracefully`() {
        // Given
        val transaction = Transaction(
            id = 3,
            transactionDate = LocalDateTime.now(),
            summary = "Zero Transaction",
            debit = BigDecimal.ZERO,
            credit = BigDecimal.ZERO
        )
        val expectedGst = BigDecimal.ZERO

        every { calculator.calculate(BigDecimal.ZERO) } returns expectedGst

        // When
        val result = mapper.from(transaction)

        // Then
        assertEquals(transaction.summary, result.summary)
        assertEquals(transaction.transactionDate, result.transactionDate)
        assertEquals(transaction.debit, result.debit)
        assertEquals(transaction.credit, result.credit)
        assertEquals(expectedGst, result.gst)
    }
}
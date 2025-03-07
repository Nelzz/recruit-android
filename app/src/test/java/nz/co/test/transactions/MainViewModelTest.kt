package nz.co.test.transactions

import app.cash.turbine.test
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import nz.co.test.transactions.activities.mappers.UiModelMapper
import nz.co.test.transactions.activities.states.UiState
import nz.co.test.transactions.repositories.TransactionRepository
import nz.co.test.transactions.viewmodels.MainViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val repository: TransactionRepository = mockk()
    private val mapper: UiModelMapper = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        every { repository.transactions } returns MutableStateFlow(Result.success(emptyList()))

        viewModel = MainViewModel(repository, mapper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        viewModel.transactions.test {
            assertEquals(UiState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Success when repository returns transactions`() = runTest {
        every { repository.transactions } returns flowOf(Result.success(emptyList()))

        every { mapper.from(any()) } answers { callOriginal() }

        viewModel = MainViewModel(repository, mapper)

        viewModel.transactions.test {
            assertEquals(UiState.Loading, awaitItem()) // Initial state
            assertTrue(awaitItem() is UiState.Success) // Mapped success state
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits Error when repository returns failure`() = runTest {
        val exception = RuntimeException("Network error")

        every { repository.transactions } returns flowOf(Result.failure(exception))

        viewModel = MainViewModel(repository, mapper)

        viewModel.transactions.test {
            assertEquals(UiState.Loading, awaitItem()) // Initial state
            assertEquals(UiState.Error(exception), awaitItem()) // Error state
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchTransactions triggers repository getTransactions`() = runTest {
        coEvery { repository.getTransactions() } just Runs

        viewModel.fetchTransactions()
        advanceUntilIdle()

        coVerify { repository.getTransactions() }
    }
}
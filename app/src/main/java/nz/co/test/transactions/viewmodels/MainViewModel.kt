package nz.co.test.transactions.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nz.co.test.transactions.activities.mappers.UiModelMapper
import nz.co.test.transactions.activities.models.UiModel
import nz.co.test.transactions.activities.states.UiState
import nz.co.test.transactions.repositories.TransactionRepository
import nz.co.test.transactions.services.Transaction
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: TransactionRepository,
    private val mapper: UiModelMapper
) : ViewModel() {

    private val _transactions = MutableStateFlow<UiState<List<UiModel>>>(UiState.Loading)
    val transactions = _transactions.asSharedFlow()

    init {
        viewModelScope.launch {
            repository.transactions.collectLatest {
                it.fold(
                    onSuccess = { value ->
                        val result = value.map { item ->
                            mapper.from(item)
                        }
                        _transactions.emit(UiState.Success(result))
                    },
                    onFailure = {
                        _transactions.emit(UiState.Error(it))
                    }
                )
            }
        }
    }

    fun fetchTransactions() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.getTransactions()
        }
    }
}
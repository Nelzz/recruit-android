package nz.co.test.transactions.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nz.co.test.transactions.MainApp
import nz.co.test.transactions.activities.models.UiModel
import nz.co.test.transactions.activities.states.UiState
import nz.co.test.transactions.databinding.ActivityMainBinding
import nz.co.test.transactions.repositories.TransactionRepository
import nz.co.test.transactions.viewmodels.MainViewModel
import nz.co.test.transactions.viewmodels.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var viewModel2: MainViewModel // ✅ Inject ViewModel directly

    @Inject
    lateinit var testing: TransactionRepository
    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MainApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        val adapter = UiModelAdapter(::onItemClick)
        binding.transactions.adapter = adapter
        binding.transactions.layoutManager = LinearLayoutManager(this)

        viewModel.fetchTransactions()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.transactions.collectLatest { state ->
                    when(state) {
                        is UiState.Success -> {
                            binding.loadingView.visibility = View.GONE
                            binding.transactions.visibility = View.VISIBLE
                            adapter.refresh(state.data)
                        }

                        is UiState.Error -> {
                            val error = state.exception
                            error.message?.let { Log.e("Error", it) }
                        }
                        UiState.Loading -> {
                            binding.loadingView.visibility = View.VISIBLE
                            binding.transactions.visibility = View.GONE
                        }
                    }
                }
            }
        }

    }

    private fun onItemClick(uiModel: UiModel) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("EXTRA_TRANSACTION", uiModel)
        }
        startActivity(intent)
    }
}


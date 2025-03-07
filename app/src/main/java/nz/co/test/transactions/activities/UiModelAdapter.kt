package nz.co.test.transactions.activities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nz.co.test.transactions.activities.models.UiModel
import nz.co.test.transactions.databinding.ItemTransactionBinding
import java.math.BigDecimal
import java.time.format.DateTimeFormatter

class UiModelAdapter(private val onItemClick: (UiModel) -> Unit) :
    RecyclerView.Adapter<UiModelAdapter.UiModelViewHolder>() {

    private val items = mutableListOf<UiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UiModelViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UiModelViewHolder(binding, onItemClick = onItemClick)
    }

    override fun onBindViewHolder(holder: UiModelViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun refresh(newItems: List<UiModel>) {

        val diffCallback = RecyclerViewDiffUtil(items, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)

    }

    class UiModelViewHolder(private val binding: ItemTransactionBinding, private val onItemClick: (UiModel) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uiModel: UiModel) {
            val formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)

            binding.textTransactionDate.text = uiModel.transactionDate.format(formatter)
            binding.textSummary.text = uiModel.summary
            binding.root.setOnClickListener {
                onItemClick(uiModel)
            }
        }
    }
}

const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm"


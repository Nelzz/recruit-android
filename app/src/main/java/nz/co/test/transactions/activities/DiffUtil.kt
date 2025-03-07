package nz.co.test.transactions.activities

import androidx.recyclerview.widget.DiffUtil
import nz.co.test.transactions.activities.models.UiModel

class RecyclerViewDiffUtil(
    private val oldList: List<UiModel>,
    private val newList: List<UiModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
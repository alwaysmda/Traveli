package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xodus.traveli.R
import com.xodus.traveli.databinding.RowFailureVerticalBinding
import com.xodus.traveli.databinding.RowLoadingVerticalBinding
import com.xodus.traveli.databinding.RowTransactionBinding
import domain.model.Transaction
import ui.base.BaseActivity
import util.Constant

class TransactionAdapter(
    private val baseActivity: BaseActivity,
    private val onRetryClick: () -> Unit,
) : ListAdapter<Transaction, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constant.CON_ADAPTER_FAILURE -> FailureVerticalHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row_failure_vertical, parent, false))
            Constant.CON_ADAPTER_LOADING -> LoadingVerticalHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row_loading_vertical, parent, false))
            else                         -> TransactionHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row_transaction, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].id) {
            Transaction.FAILURE_ID -> Constant.CON_ADAPTER_FAILURE
            Transaction.LOADING_ID -> Constant.CON_ADAPTER_LOADING
            else                   -> Constant.CON_ADAPTER_NORMAL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TransactionHolder     -> holder.bind(getItem(position))
            is FailureVerticalHolder -> holder.bind()
        }
    }

    inner class LoadingVerticalHolder(val binding: RowLoadingVerticalBinding) : RecyclerView.ViewHolder(binding.root)
    inner class FailureVerticalHolder(val binding: RowFailureVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            with(binding) {
                app = baseActivity.app
                binding.rowFailureBtnRetry.setOnClickListener {
                    onRetryClick()
                }
            }
        }
    }

    inner class TransactionHolder(val binding: RowTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            with(binding) {
                app = baseActivity.app
                data = transaction
                executePendingBindings()
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) =
            oldItem == newItem
    }
}
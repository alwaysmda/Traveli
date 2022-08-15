package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xodus.traveli.R
import com.xodus.traveli.databinding.RowChargePriceBinding
import domain.model.PriceItem
import ui.base.BaseActivity

class ChargePriceAdapter(
    private val baseActivity: BaseActivity,
    private val onItemClick: (Int, PriceItem) -> Unit,
) : ListAdapter<PriceItem, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PriceItemHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row_charge_price, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PriceItemHolder -> holder.bind(getItem(position))
        }
    }

    inner class PriceItemHolder(val binding: RowChargePriceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(priceItem: PriceItem) {
            with(binding) {
                app = baseActivity.app
                data = priceItem
                executePendingBindings()
                rowChargePriceCvContent.setOnClickListener {
                    onItemClick(bindingAdapterPosition, priceItem)
                }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<PriceItem>() {
        override fun areItemsTheSame(oldItem: PriceItem, newItem: PriceItem) =
            oldItem.value == newItem.value

        override fun areContentsTheSame(oldItem: PriceItem, newItem: PriceItem) =
            oldItem == newItem
    }
}
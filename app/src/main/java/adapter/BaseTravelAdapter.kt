package adapter

import adapter.commonViewHolders.FailureVerticalHolder
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import domain.model.TravelPreview
import util.Constant.CON_ADAPTER_FAILURE
import util.Constant.CON_ADAPTER_LOADING
import util.Constant.CON_ADAPTER_NORMAL

abstract class BaseTravelAdapter() : ListAdapter<TravelPreview, RecyclerView.ViewHolder>(DiffCallback()) {

    abstract inner class TravelViewHolder(private val baseBinding: ViewBinding) : RecyclerView.ViewHolder(baseBinding.root) {
        abstract fun bind(travel: TravelPreview)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TravelViewHolder      -> holder.bind(currentList[position])
            is FailureVerticalHolder -> holder.bind()
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].id) {
            TravelPreview.LOADING_ID -> CON_ADAPTER_LOADING
            TravelPreview.FAILURE_ID -> CON_ADAPTER_FAILURE
            else                     -> CON_ADAPTER_NORMAL
        }
    }


    protected class DiffCallback : DiffUtil.ItemCallback<TravelPreview>() {
        override fun areItemsTheSame(oldItem: TravelPreview, newItem: TravelPreview) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TravelPreview, newItem: TravelPreview) =
            oldItem == newItem
    }

}
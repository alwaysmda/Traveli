package adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import domain.model.TravelPreview

abstract class BaseTravelAdapter() : ListAdapter<TravelPreview, BaseTravelAdapter.TravelViewHolder>(DiffCallback()) {

    abstract inner class TravelViewHolder(private val baseBinding: ViewBinding) : RecyclerView.ViewHolder(baseBinding.root) {
        abstract fun bind(travel: TravelPreview)
    }


    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    protected class DiffCallback : DiffUtil.ItemCallback<TravelPreview>() {
        override fun areItemsTheSame(oldItem: TravelPreview, newItem: TravelPreview) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TravelPreview, newItem: TravelPreview) =
            oldItem == newItem
    }

}
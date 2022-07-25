package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.RowSquareTravelBinding
import domain.model.TravelPreview
import ui.base.BaseActivity

class SquareTravelAdapter(private val activity: BaseActivity, private val onItemClick: (travelPreview: TravelPreview, pos: Int) -> Unit) : ListAdapter<TravelPreview, SquareTravelAdapter.TravelViewHolder>(DiffCallback()) {


    inner class TravelViewHolder(private val binding: RowSquareTravelBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(travelPreview: TravelPreview) {
            binding.apply {
                app = activity.app
                data = travelPreview
                ivTravel.clipToOutline = true
                root.setOnClickListener {
                    onItemClick(travelPreview, bindingAdapterPosition)
                }
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {
        return TravelViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_square_travel,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    private class DiffCallback : DiffUtil.ItemCallback<TravelPreview>() {
        override fun areItemsTheSame(oldItem: TravelPreview, newItem: TravelPreview) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TravelPreview, newItem: TravelPreview) =
            oldItem == newItem
    }
}
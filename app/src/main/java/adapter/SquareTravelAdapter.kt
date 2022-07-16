package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.RowSquareTravelBinding
import domain.model.travel.Travel
import ui.base.BaseActivity

class SquareTravelAdapter(private val activity:BaseActivity):ListAdapter<Travel,SquareTravelAdapter.TravelViewHolder>(DiffCallback()) {



    inner class TravelViewHolder(private val binding:RowSquareTravelBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(travel: Travel){
            binding.apply {
                app = activity.app
                data = travel
                ivTravel.clipToOutline = true
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


    private class DiffCallback : DiffUtil.ItemCallback<Travel>() {
        override fun areItemsTheSame(oldItem: Travel, newItem: Travel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Travel, newItem: Travel) =
            oldItem == newItem
    }
}
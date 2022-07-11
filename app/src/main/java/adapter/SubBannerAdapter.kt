package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.RowSubBannerBinding
import com.xodus.templatefive.databinding.RowTravelBinding
import domain.model.travel.Travel
import ui.base.BaseActivity

class SubBannerAdapter(private val activity:BaseActivity):ListAdapter<Travel,SubBannerAdapter.SubBannerViewHolder>(DiffCallback()) {

    inner class SubBannerViewHolder(private val binding: RowSubBannerBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(travel: Travel){
            binding.apply {
                app = activity.app
                data = travel

            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubBannerViewHolder {
        return SubBannerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_sub_banner,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SubBannerViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    private class DiffCallback : DiffUtil.ItemCallback<Travel>() {
        override fun areItemsTheSame(oldItem: Travel, newItem: Travel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Travel, newItem: Travel) =
            oldItem == newItem
    }

}
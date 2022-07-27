package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xodus.traveli.R
import com.xodus.traveli.databinding.RowTravelBinding
import domain.model.TravelPreview
import ui.base.BaseActivity

class TravelAdapter(private val activity: BaseActivity) : ListAdapter<TravelPreview, TravelAdapter.SubBannerViewHolder>(DiffCallback()) {

    inner class SubBannerViewHolder(private val binding: RowTravelBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(travelPreview: TravelPreview) {
            binding.apply {
                app = activity.app
                data = travelPreview

            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubBannerViewHolder {
        return SubBannerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_travel,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SubBannerViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    private class DiffCallback : DiffUtil.ItemCallback<TravelPreview>() {
        override fun areItemsTheSame(oldItem: TravelPreview, newItem: TravelPreview) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TravelPreview, newItem: TravelPreview) =
            oldItem == newItem
    }

}
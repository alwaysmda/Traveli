package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xodus.traveli.R
import com.xodus.traveli.databinding.RowTravelAddBinding
import com.xodus.traveli.databinding.RowTravelBinding
import domain.model.TravelPreview
import ui.base.BaseActivity

class TravelAdapter(
    private val activity: BaseActivity,
    private val onAddClick: () -> (Unit) = {},
    private val onClick: (Int, TravelPreview) -> (Unit),
) : ListAdapter<TravelPreview, RecyclerView.ViewHolder>(DiffCallback()) {
    inner class SubBannerViewHolder(private val binding: RowTravelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelPreview: TravelPreview) {
            binding.apply {
                app = activity.app
                data = travelPreview
                rowTravelCvParent.setOnClickListener {
                    onClick(bindingAdapterPosition, travelPreview)
                }
            }
        }
    }

    inner class AddTravelViewHolder(private val binding: RowTravelAddBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                app = activity.app
                rowTravelAddBtnAdd.setOnClickListener {
                    onAddClick()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].id == TravelPreview.ADD_ID) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            AddTravelViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_travel_add,
                    parent,
                    false
                )
            )
        } else {
            SubBannerViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_travel,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SubBannerViewHolder -> holder.bind(currentList[position])
            is AddTravelViewHolder -> holder.bind()
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<TravelPreview>() {
        override fun areItemsTheSame(oldItem: TravelPreview, newItem: TravelPreview) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: TravelPreview, newItem: TravelPreview) =
            oldItem == newItem
    }
}
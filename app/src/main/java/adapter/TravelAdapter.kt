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
import com.xodus.traveli.databinding.RowTravelAddBinding
import com.xodus.traveli.databinding.RowTravelBinding
import domain.model.TravelPreview
import domain.model.TravelPreview.Companion.ADD_ID
import domain.model.TravelPreview.Companion.FAILURE_ID
import domain.model.TravelPreview.Companion.LOADING_ID
import ui.base.BaseActivity
import util.Constant.CON_ADAPTER_FAILURE
import util.Constant.CON_ADAPTER_LOADING
import util.Constant.CON_ADAPTER_NORMAL

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

    inner class LoadingVerticalHolder(val binding: RowLoadingVerticalBinding) : RecyclerView.ViewHolder(binding.root)
    inner class FailureVerticalHolder(val binding: RowFailureVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            with(binding) {
                app = activity.app
                binding.rowFailureBtnRetry.setOnClickListener {

                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].id) {
            LOADING_ID -> CON_ADAPTER_LOADING
            ADD_ID     -> 0
            FAILURE_ID -> CON_ADAPTER_FAILURE
            else       -> CON_ADAPTER_NORMAL

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0                   -> AddTravelViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_travel_add,
                    parent,
                    false
                )
            )
            CON_ADAPTER_NORMAL  -> SubBannerViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_travel,
                    parent,
                    false
                )
            )
            CON_ADAPTER_LOADING ->
                LoadingVerticalHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_loading_vertical,
                        parent,
                        false
                    )
                )
            CON_ADAPTER_FAILURE ->
                FailureVerticalHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_failure_vertical,
                        parent,
                        false
                    )
                )
            else                -> SubBannerViewHolder(
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
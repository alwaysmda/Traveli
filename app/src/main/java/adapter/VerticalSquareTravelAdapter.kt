package adapter

import adapter.commonViewHolders.FailureVerticalHolder
import adapter.commonViewHolders.LoadingVerticalHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.xodus.traveli.R
import com.xodus.traveli.databinding.RowTravelVerticalBinding
import domain.model.TravelPreview
import ui.base.BaseActivity
import util.Constant.CON_ADAPTER_FAILURE
import util.Constant.CON_ADAPTER_LOADING
import util.Constant.CON_ADAPTER_NORMAL

class VerticalSquareTravelAdapter(private val activity: BaseActivity, private val onItemClick: (travelPreview: TravelPreview, pos: Int) -> Unit) : BaseTravelAdapter() {

    inner class VerticalSquareTravelAdapter(private val binding: RowTravelVerticalBinding) : TravelViewHolder(binding) {
        override fun bind(travel: TravelPreview) {
            binding.apply {
                app = activity.app
                data = travel
                cvParent.setOnClickListener {
                    onItemClick(travel, bindingAdapterPosition)
                }

            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            CON_ADAPTER_LOADING -> {
                LoadingVerticalHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_loading_vertical,
                        parent,
                        false
                    )
                )
            }

            CON_ADAPTER_NORMAL  -> {
                VerticalSquareTravelAdapter(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_travel_vertical,
                        parent,
                        false
                    )
                )
            }

            CON_ADAPTER_FAILURE -> {
                FailureVerticalHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_failure_vertical,
                        parent,
                        false
                    ),
                    activity
                )
            }

            else                -> VerticalSquareTravelAdapter(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_travel_vertical,
                    parent,
                    false
                )
            )

        }


    }


}

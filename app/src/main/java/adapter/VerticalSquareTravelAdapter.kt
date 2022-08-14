package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.xodus.traveli.R
import com.xodus.traveli.databinding.RowTravelVerticalBinding
import domain.model.TravelPreview
import ui.base.BaseActivity

class VerticalSquareTravelAdapter(private val activity: BaseActivity) : BaseTravelAdapter() {

    inner class VerticalSquareTravelAdapter(private val binding: RowTravelVerticalBinding) : TravelViewHolder(binding) {
        override fun bind(travel: TravelPreview) {
            binding.apply {
                app = activity.app
                data = travel
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): TravelViewHolder {
        return VerticalSquareTravelAdapter(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_travel_vertical,
                parent,
                false
            )
        )
    }


}
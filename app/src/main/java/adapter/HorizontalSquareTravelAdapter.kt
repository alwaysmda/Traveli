package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.xodus.traveli.R
import com.xodus.traveli.databinding.RowSquareTravelBinding
import domain.model.TravelPreview
import ui.base.BaseActivity

class HorizontalSquareTravelAdapter(private val activity: BaseActivity, private val onItemClick: (travelPreview: TravelPreview, pos: Int) -> Unit) : BaseTravelAdapter() {


    inner class HorizontalSquareTravelViewHolder(val binding: RowSquareTravelBinding) : BaseTravelAdapter.TravelViewHolder(binding) {


        override fun bind(travel: TravelPreview) {
            binding.apply {
                app = activity.app
                data = travel
                ivTravel.clipToOutline = true
                root.setOnClickListener {
                    onItemClick(travel, bindingAdapterPosition)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): TravelViewHolder {
        return HorizontalSquareTravelViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(
                    parent.context
                ), R.layout.row_square_travel,
                parent,
                false
            )
        )
    }


}


//override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalSquareTravelAdapter.HorizontalSquareTravelViewHolder {
//    return HorizontalSquareTravelAdapter.HorizontalSquareTravelViewHolder()
//}
//
//override fun onBindViewHolder(p0: BaseTravelAdapter.TravelViewHolder, p1: Int) {
//
//}
//
//override fun onBindViewHolder(holder: HorizontalSquareTravelViewHolder, position: Int) {
//    holder.bind(currentList[position])
//}


package adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.RowCityBinding
import domain.model.City
import ui.base.BaseActivity


class CityAdapter(private val activity: BaseActivity) : ListAdapter<City, CityAdapter.CityViewHolder>(DiffCallback()) {


    inner class CityViewHolder(private val binding: RowCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            binding.apply {
                app = activity.app
                data = city

            }
        }
    }


    private class DiffCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: City, newItem: City) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CityViewHolder {
        return CityViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_city,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}
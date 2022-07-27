package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xodus.traveli.R
import com.xodus.traveli.databinding.RowStatBinding
import domain.model.Stat
import main.ApplicationClass

class StatAdapter(
    private val app: ApplicationClass,
    private val onItemClick: (Int, Stat) -> Unit = { _, _ -> },
) : ListAdapter<Stat, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PhotoHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row_stat, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoHolder -> holder.bind(getItem(position))
        }
    }

    inner class PhotoHolder(val binding: RowStatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(Stat: Stat) {
            with(binding) {
                app = this@StatAdapter.app
                data = Stat
                executePendingBindings()
                rowStatCvContent.setOnClickListener { onItemClick(bindingAdapterPosition, Stat) }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Stat>() {
        override fun areItemsTheSame(oldItem: Stat, newItem: Stat) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Stat, newItem: Stat) =
            oldItem == newItem
    }
}
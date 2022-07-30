package adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xodus.traveli.databinding.RowSimpleTextBinding


class SimpleAdapter : ListAdapter<String, SimpleAdapter.SimpleViewHolder>(DiffCallback()) {


    inner class SimpleViewHolder(private val binding: RowSimpleTextBinding) : RecyclerView.ViewHolder(binding.root)


    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SimpleViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(p0: SimpleViewHolder, p1: Int) {
        TODO("Not yet implemented")
    }
}
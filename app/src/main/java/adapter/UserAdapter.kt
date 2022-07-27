package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xodus.traveli.R
import com.xodus.traveli.databinding.RowUserBinding
import domain.model.UserPreview
import ui.base.BaseActivity

class UserAdapter(private val activity: BaseActivity) : ListAdapter<UserPreview, UserAdapter.UserViewHolder>(DiffCallback()) {
    inner class UserViewHolder(private val binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userPreview: UserPreview) {
            binding.apply {
                app = activity.app
                data = userPreview
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private class DiffCallback : DiffUtil.ItemCallback<UserPreview>() {
        override fun areItemsTheSame(oldItem: UserPreview, newItem: UserPreview) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserPreview, newItem: UserPreview) =
            oldItem == newItem
    }
}
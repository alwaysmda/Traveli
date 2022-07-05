package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.RowPhotoStateBinding
import com.xodus.templatefive.databinding.RowUserBinding
import ui.base.BaseActivity

class UserAdapter(
    private val baseActivity: BaseActivity,
    private val onItemClick: (Int, String) -> Unit = { _, _ -> },
    private val onRetryClick: () -> Unit = {},
) : ListAdapter<String, RecyclerView.ViewHolder>(DiffCallback()) {
    private var loading = false
    private var retry = false

    fun setLoading(loading: Boolean) {
        if (this.loading == loading) {
            return
        }
        retry = false
        this.loading = loading
        if (loading) {
            notifyItemInserted(currentList.size)
        } else {
            notifyItemRemoved(currentList.size)
        }
    }

    fun setRetry() {
        retry = true
        notifyItemChanged(currentList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (loading && viewType == currentList.size) {
            LoadingHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row_photo_state, parent, false))
        } else {
            UserHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row_user, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return currentList.size + if (loading) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LoadingHolder -> holder.bind()
            is UserHolder    -> holder.bind(getItem(position))
        }
    }

    inner class UserHolder(val binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(username: String) {
            with(binding) {
                app = baseActivity.app
                data = username
                executePendingBindings()
                rowChatCvContent.setOnClickListener { onItemClick(bindingAdapterPosition, username) }
            }
        }
    }

    inner class LoadingHolder(val binding: RowPhotoStateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            with(binding) {
                app = baseActivity.app
                rowPhotoBtnRetry.isVisible = retry
                rowPhotoPbLoading.isVisible = retry.not()
                executePendingBindings()
                rowPhotoBtnRetry.setOnClickListener { onRetryClick() }
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }
}
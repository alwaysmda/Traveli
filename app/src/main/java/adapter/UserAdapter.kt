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
import com.xodus.traveli.databinding.RowUserBinding
import domain.model.UserPreview
import ui.base.BaseActivity
import util.Constant.CON_ADAPTER_FAILURE
import util.Constant.CON_ADAPTER_LOADING
import util.Constant.CON_ADAPTER_NORMAL

class UserAdapter(private val activity: BaseActivity, private val onItemClick: (user: UserPreview, pos: Int) -> Unit) : ListAdapter<UserPreview, RecyclerView.ViewHolder>(DiffCallback()) {
    inner class UserViewHolder(private val binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userPreview: UserPreview) {
            binding.apply {
                app = activity.app
                data = userPreview
                cvParent.setOnClickListener {
                    onItemClick(userPreview, bindingAdapterPosition)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CON_ADAPTER_FAILURE -> {
                FailureVerticalHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_failure_vertical,
                        parent,
                        false
                    )
                )
            }

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
                UserViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_user,
                        parent,
                        false
                    )
                )
            }

            else                -> UserViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_user,
                    parent,
                    false
                )
            )
        }


    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].id) {
            UserPreview.FAILURE_ID -> CON_ADAPTER_FAILURE
            UserPreview.LOADING_ID -> CON_ADAPTER_LOADING
            else                   -> CON_ADAPTER_NORMAL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder        -> holder.bind(currentList[position])
            is FailureVerticalHolder -> holder.bind()

        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<UserPreview>() {
        override fun areItemsTheSame(oldItem: UserPreview, newItem: UserPreview) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserPreview, newItem: UserPreview) =
            oldItem == newItem
    }


}
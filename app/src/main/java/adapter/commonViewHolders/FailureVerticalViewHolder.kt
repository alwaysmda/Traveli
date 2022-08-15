package adapter.commonViewHolders

import androidx.recyclerview.widget.RecyclerView
import com.xodus.traveli.databinding.RowFailureVerticalBinding
import ui.base.BaseActivity

class FailureVerticalHolder(val binding: RowFailureVerticalBinding, private val activity: BaseActivity) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        with(binding) {
            app = activity.app
            binding.rowFailureBtnRetry.setOnClickListener {

            }
        }
    }
}
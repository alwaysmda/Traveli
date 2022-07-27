package ui.base

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.xodus.traveli.R
import com.xodus.traveli.databinding.ContentWrapperBinding
import main.ApplicationClass

class ContentWrapper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var _binding: ContentWrapperBinding? = null
    private val binding: ContentWrapperBinding get() = _binding!!

    //
    private lateinit var app: ApplicationClass
    private var onRefreshClicked: () -> (Unit) = {}

    //
    private var status: WrapperStatus = WrapperStatus.Loading
    private val contentList = arrayListOf<View?>()

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        when (status) {
            is WrapperStatus.Loading -> {
                bundle.putInt("STATUS", 0)
            }
            is WrapperStatus.Failure -> {
                bundle.putInt("STATUS", 1)
                bundle.putString("MESSAGE", (status as WrapperStatus.Failure).message)
            }
            is WrapperStatus.Success -> {
                bundle.putInt("STATUS", 2)
            }
        }
        val superState = super.onSaveInstanceState()
        bundle.putParcelable("SUPER", superState)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val statusInt = state.getInt("STATUS")
            status = when (statusInt) {
                0    -> WrapperStatus.Loading
                1    -> {
                    val message = state.getString("MESSAGE") ?: ""
                    WrapperStatus.Failure(message)
                }
                else -> WrapperStatus.Success
            }
            setStatus(status)
            val superState = state.getParcelable<Parcelable>("SUPER")
            super.onRestoreInstanceState(superState)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    init {
        if (isInEditMode.not()) {
            _binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.content_wrapper, this, true)
            binding.cwBtnRefresh.setOnClickListener {
                onRefreshClicked()
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setStatus(status)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
        if (child?.id != R.id.cw_clParent) {
            contentList.add(child)
        }
        //        if (_binding == null) {
        //            super.addView(child, params)
        //        } else {
        //            binding.cwFlContent.addView(child, params)
        //        }
    }

    sealed class WrapperStatus {
        object Loading : WrapperStatus()
        class Failure(val message: String) : WrapperStatus()
        object Success : WrapperStatus()
    }

    fun setStatus(wrapperStatus: WrapperStatus) {
        status = wrapperStatus
        if (isInEditMode) {
            status = WrapperStatus.Success
        }
        binding.apply {
            cwPbContentLoading1.isVisible = status is WrapperStatus.Loading
            cwPbContentLoading2.isVisible = status is WrapperStatus.Loading
            cwFlRetry.isVisible = status is WrapperStatus.Failure
            cwTvError.text = (status as? WrapperStatus.Failure)?.message
        }
        contentList.forEach {
            it?.isVisible = status is WrapperStatus.Success
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("onRefreshClick")
        fun ContentWrapper.setOnRefreshClick(onRefreshClickListener: () -> (Unit)) {
            this.onRefreshClicked = onRefreshClickListener
        }

        @JvmStatic
        @BindingAdapter("app")
        fun ContentWrapper.setApp(app: ApplicationClass) {
            this.app = app
        }
    }
}
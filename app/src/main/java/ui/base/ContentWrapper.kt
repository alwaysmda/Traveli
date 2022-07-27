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
    private var emptyMessage: String = ""
    private var emptyIcon: Int = R.drawable.ic_sad
    private var onRetryClickListener: ContentWrapperInterface? = null
    //    private var onRetryClicked: () -> (Unit) = {}
    //
    private var status: WrapperStatus = WrapperStatus.Loading
    private val contentList = arrayListOf<View?>()

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        when (status) {
            is WrapperStatus.Loading -> {
                bundle.putInt(ARG_STATUS, 0)
            }
            is WrapperStatus.Success -> {
                bundle.putInt(ARG_STATUS, 1)
            }
            is WrapperStatus.Empty   -> {
                bundle.putInt(ARG_STATUS, 2)
            }
            is WrapperStatus.Failure -> {
                bundle.putInt(ARG_STATUS, 3)
                bundle.putString(ARG_MESSAGE, (status as WrapperStatus.Failure).message)
            }
        }
        val superState = super.onSaveInstanceState()
        bundle.putParcelable(ARG_SUPER, superState)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val statusInt = state.getInt(ARG_STATUS)
            status = when (statusInt) {
                0    -> WrapperStatus.Loading
                1    -> WrapperStatus.Success
                2    -> WrapperStatus.Empty
                else -> WrapperStatus.Failure(state.getString(ARG_MESSAGE) ?: "")
            }
            setStatus(status)
            val superState = state.getParcelable<Parcelable>(ARG_SUPER)
            super.onRestoreInstanceState(superState)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    init {
        if (isInEditMode.not()) {
            _binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.content_wrapper, this, true)
            binding.cwBtnRetry.setOnClickListener {
                //                onRetryClicked()
                onRetryClickListener?.onRetryClick()
            }
            val a = context.obtainStyledAttributes(attrs, R.styleable.ContentWrapper, defStyleAttr, 0)
            emptyIcon = a.getResourceId(R.styleable.ContentWrapper_emptyIcon, R.drawable.ic_sad)
            a.recycle()
            binding.cwIvEmpty.setImageResource(emptyIcon)
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
            cwFlEmpty.isVisible = status is WrapperStatus.Empty
        }
        contentList.forEach {
            it?.isVisible = status is WrapperStatus.Success
        }
    }

    fun updateEmptyMessage(message: String) {
        emptyMessage = message
        binding.cwTvEmpty.text = emptyMessage
    }

    sealed class WrapperStatus {
        object Loading : WrapperStatus()
        object Success : WrapperStatus()
        object Empty : WrapperStatus()
        class Failure(val message: String) : WrapperStatus()
    }

    interface ContentWrapperInterface {
        fun onRetryClick()
    }

    companion object {
        const val ARG_SUPER: String = "ARG_SUPER"
        const val ARG_STATUS: String = "ARG_STATUS"
        const val ARG_MESSAGE: String = "ARG_MESSAGE"

        @JvmStatic
        @BindingAdapter("onRetryClick")
        fun ContentWrapper.setOnRetryClick(onRetryClickListener: ContentWrapperInterface) {
            this.onRetryClickListener = onRetryClickListener
        }

        @JvmStatic
        @BindingAdapter("app")
        fun ContentWrapper.setApp(app: ApplicationClass) {
            this.app = app
        }

        @JvmStatic
        @BindingAdapter("emptyMessage")
        fun ContentWrapper.setEmptyMessage(message: String) {
            this.updateEmptyMessage(message)
        }
    }
}
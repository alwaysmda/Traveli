package ui.dialog

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xodus.traveli.R
import com.xodus.traveli.databinding.SheetConfirmBinding
import ui.base.BaseActivity

class ConfirmBottomSheet(
    private val baseActivity: BaseActivity,
    private val icon: Int?,
    private val iconColor: Int?,
    private val title: String,
    private val value: String?,
    private val positive: String?,
    private val onPositive: () -> Unit,
    private val negative: String?,
    private val onNegative: () -> Unit,
) : BottomSheetDialogFragment() {
    private var _binding: SheetConfirmBinding? = null
    val binding: SheetConfirmBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate<SheetConfirmBinding>(inflater, R.layout.sheet_confirm, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.app, baseActivity.app)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            sheetEditTvTitle.text = title
            sheetEditTvContent.text = value
            icon?.let {
                sheetEditIvIcon.isVisible = true
                sheetEditIvIcon.setImageResource(icon)
                iconColor?.let {
                    sheetEditIvIcon.setColorFilter(ContextCompat.getColor(requireContext(), iconColor), PorterDuff.Mode.SRC_IN)
                }
            } ?: kotlin.run {
                sheetEditIvIcon.isVisible = false
            }
            templateBtnPositive.isVisible = positive != null
            templateBtnPositive.text = positive
            templateBtnPositive.setOnClickListener {
                onPositive()
                dismiss()
            }
            templateBtnNegative.isVisible = negative != null
            templateBtnNegative.text = negative
            templateBtnNegative.setOnClickListener {
                onNegative()
                dismiss()
            }
        }
    }

    fun showLoading(show: Boolean) {
        binding.sheetEditFlLoading.isVisible = show
    }

    fun showError(error: String?) {
        binding.sheetEditTvError.isVisible = error != null
        binding.sheetEditTvError.text = error
    }
}
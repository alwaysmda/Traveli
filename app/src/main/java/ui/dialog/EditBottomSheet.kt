package ui.dialog

import android.graphics.PorterDuff
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xodus.traveli.R
import com.xodus.traveli.databinding.SheetEditBinding
import ui.base.BaseActivity

class EditBottomSheet(
    private val baseActivity: BaseActivity,
    private val icon: Int?,
    private val iconColor: Int?,
    private val title: String,
    private val value: String?,
    private val isMultiline: Boolean = false,
    private val onPositive: (String?) -> Unit
) : BottomSheetDialogFragment() {
    private var _binding: SheetEditBinding? = null
    val binding: SheetEditBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate<SheetEditBinding>(inflater, R.layout.sheet_edit, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.app, baseActivity.app)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            sheetEditTvTitle.text = title
            sheetEditEtContent.setText(value)
            sheetEditEtContent.inputType = if (isMultiline) InputType.TYPE_TEXT_FLAG_MULTI_LINE else InputType.TYPE_CLASS_TEXT
            sheetEditEtContent.isSingleLine = isMultiline.not()
            icon?.let {
                sheetEditIvIcon.isVisible = true
                sheetEditIvIcon.setImageResource(icon)
                iconColor?.let {
                    sheetEditIvIcon.setColorFilter(ContextCompat.getColor(requireContext(), iconColor), PorterDuff.Mode.SRC_IN)
                }
            } ?: kotlin.run {
                sheetEditIvIcon.isVisible = false
            }
            templateBtnPositive.setOnClickListener {
                showError(null)
                showLoading(true)
                val value = sheetEditEtContent.text.toString()
                onPositive(value.ifBlank { null })
            }
            templateBtnNegative.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.sheetEditFlLoading.isVisible = show
    }

    fun showError(error: String?) {
        showLoading(false)
        binding.sheetEditTvError.text = error
    }
}
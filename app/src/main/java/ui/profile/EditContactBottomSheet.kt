package ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.SheetEditContactBinding

class EditContactBottomSheet(private val viewModel: ProfileViewModel, private val title: String, private val content: String?, private val onPositive: (String?) -> Unit) : BottomSheetDialogFragment() {
    private var _binding: SheetEditContactBinding? = null
    val binding: SheetEditContactBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate<SheetEditContactBinding>(inflater, R.layout.sheet_edit_contact, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.vm, viewModel)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            editContactTvTitle.text = title
            editContactEtContent.setText(content)
            templateBtnPositive.setOnClickListener {
                showError(null)
                showLoading(true)
                onPositive(editContactEtContent.text?.toString())
            }
            templateBtnNegative.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.editContactFlLoading.isVisible = show
    }

    fun showError(error: String?) {
        showLoading(false)
        binding.editContactTvError.text = error
    }
}
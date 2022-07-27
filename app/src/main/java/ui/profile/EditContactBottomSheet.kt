package ui.profile

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
import com.xodus.traveli.databinding.SheetEditContactBinding
import domain.model.ContactItem

class EditContactBottomSheet(private val viewModel: ProfileViewModel, private val content: ContactItem, private val onPositive: (ContactItem) -> Unit) : BottomSheetDialogFragment() {
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
            editContactTvTitle.text = content.title
            editContactEtContent.setText(content.value)
            editContactIvIcon.setImageResource(content.icon)
            editContactIvIcon.setColorFilter(ContextCompat.getColor(requireContext(), content.color), PorterDuff.Mode.SRC_IN)
            templateBtnPositive.setOnClickListener {
                showError(null)
                showLoading(true)
                val value = editContactEtContent.text.toString()
                onPositive(content.copy(value = value.ifBlank { null }))
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
package ui.profile_edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.xodus.traveli.R
import com.xodus.traveli.databinding.FragmentProfileEditBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment
import ui.dialog.ConfirmBottomSheet
import ui.dialog.EditBottomSheet
import util.extension.log

@AndroidEntryPoint
class ProfileEditFragment : BaseFragment<FragmentProfileEditBinding, ProfileEditEvents, ProfileEditAction, ProfileEditViewModel>(R.layout.fragment_profile_edit) {
    private var editBottomSheet: EditBottomSheet? = null
    private var confirmBottomSheet: ConfirmBottomSheet? = null
    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(requireContext()) // optional usage
            log("CROP:${uriContent}:${uriFilePath}")
            viewModel.action.onImageSelect(uriFilePath)
        } else {
            // an error occurred
            val exception = result.error
            log("CROP:${exception}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: ProfileEditViewModel by viewModels()
        initialize(vm)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeToEvents()
        super.onViewCreated(view, savedInstanceState)
        viewModel.action.onStart()
    }

    private fun observeToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.event.collect {
            when (it) {
                is ProfileEditEvents.Snack               -> snack(it.message)
                is ProfileEditEvents.NavBack             -> findNavController().popBackStack()
                is ProfileEditEvents.EditContent         -> editBottomSheet = EditBottomSheet(baseActivity, null, null, it.title, it.value, it.multiline) { value ->
                    viewModel.onConfirmEditClick(value)
                }.also { sheet ->
                    sheet.show(childFragmentManager, EditBottomSheet::class.simpleName)
                }
                is ProfileEditEvents.EditContentComplete -> editBottomSheet?.dismiss()
                is ProfileEditEvents.EditContentError    -> editBottomSheet?.showError(it.error)
                is ProfileEditEvents.PickImage           -> cropImage.launch(
                    options {
                        setGuidelines(CropImageView.Guidelines.ON)
                        setAspectRatio(it.aspectRatioX, it.aspectRatioY)
                        setCropMenuCropButtonTitle(viewModel.app.m.crop)
                    }
                )
                is ProfileEditEvents.ShowDialog          -> confirmBottomSheet = ConfirmBottomSheet(
                    baseActivity,
                    it.icon,
                    it.iconColor,
                    it.title,
                    it.content,
                    it.positive,
                    it.onPositive,
                    it.negative,
                    it.onNegative
                ).also { sheet ->
                    sheet.show(childFragmentManager, EditBottomSheet::class.simpleName)
                }
                is ProfileEditEvents.ShowLoading         -> showLoading(it.show)
            }
        }
    }
}
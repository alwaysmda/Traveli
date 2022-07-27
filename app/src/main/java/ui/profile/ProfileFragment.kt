package ui.profile

import adapter.StatAdapter
import adapter.TravelAdapter
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.load
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.xodus.traveli.R
import com.xodus.traveli.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment
import ui.base.ContentWrapper
import util.extension.log

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileEvents, ProfileAction, ProfileViewModel>(R.layout.fragment_profile) {
    private var editContactBottomSheet: EditContactBottomSheet? = null
    private val args by navArgs<ProfileFragmentArgs>()
    private var travelAdapter: TravelAdapter? = null
    private var statAdapter: StatAdapter? = null
    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(requireContext()) // optional usage
            log("CROP:${uriContent}:${uriFilePath}")
        } else {
            // an error occurred
            val exception = result.error
            log("CROP:${exception}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: ProfileViewModel by viewModels()
        initialize(vm)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeToEvents()
        super.onViewCreated(view, savedInstanceState)
        viewModel.action.onStart(args.userId)
    }

    private fun observeToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.event.collect {
            when (it) {
                is ProfileEvents.Snack                -> snack(it.message)
                is ProfileEvents.NavSetting           -> {
                    cropImage.launch(
                        options {
                            setGuidelines(CropImageView.Guidelines.ON)
                            setAspectRatio(2, 1)
                            setCropMenuCropButtonTitle(viewModel.app.m.crop)
                        }
                    )
                }
                is ProfileEvents.NavTravel            -> Unit
                is ProfileEvents.NavAddTravel         -> Unit
                is ProfileEvents.NavTransactionList   -> Unit
                is ProfileEvents.SetUserLoading       -> binding.profileCwBio.setStatus(ContentWrapper.WrapperStatus.Loading)
                is ProfileEvents.SetUserFailure       -> binding.profileCwBio.setStatus(ContentWrapper.WrapperStatus.Failure(it.message))
                is ProfileEvents.UpdateUser           -> {
                    binding.apply {
                        profileCwBio.setStatus(ContentWrapper.WrapperStatus.Success)
                        profileBtnSetting.isVisible = it.isMe
                        profileCwBalance.isVisible = it.isMe
                        profileIvCover.load(it.user.cover)
                        profileIvAvatar.load(it.user.avatar)
                        profileTvName.text = it.user.name
                        profileTvHometown.text = it.user.hometown
                        profileTvBio.text = it.user.bio
                        it.user.contact.apply {
                            profileBtnEmail.backgroundTintList = ContextCompat.getColorStateList(requireContext(), if (email.value == null) R.color.md_grey_500 else email.color)
                            profileBtnPhone.backgroundTintList = ContextCompat.getColorStateList(requireContext(), if (phone.value == null) R.color.md_grey_500 else phone.color)
                            profileBtnTwitter.backgroundTintList = ContextCompat.getColorStateList(requireContext(), if (twitter.value == null) R.color.md_grey_500 else twitter.color)
                            profileBtnInstagram.backgroundTintList = ContextCompat.getColorStateList(requireContext(), if (instagram.value == null) R.color.md_grey_500 else instagram.color)
                            profileBtnWebsite.backgroundTintList = ContextCompat.getColorStateList(requireContext(), if (website.value == null) R.color.md_grey_500 else website.color)
                        }
                    }
                }
                is ProfileEvents.SetBalanceLoading    -> binding.profileCwBalance.setStatus(ContentWrapper.WrapperStatus.Loading)
                is ProfileEvents.SetBalanceFailure    -> binding.profileCwBalance.setStatus(ContentWrapper.WrapperStatus.Failure(it.message))
                is ProfileEvents.UpdateBalance        -> {
                    binding.profileCwBalance.setStatus(ContentWrapper.WrapperStatus.Success)
                    binding.profileTvBalance.text = it.balance
                }
                is ProfileEvents.SetTravelListLoading -> binding.profileCwTravel.setStatus(ContentWrapper.WrapperStatus.Loading)
                is ProfileEvents.SetTravelListFailure -> binding.profileCwTravel.setStatus(ContentWrapper.WrapperStatus.Failure(it.message))
                is ProfileEvents.UpdateTravelList     -> {
                    binding.profileCwTravel.setStatus(ContentWrapper.WrapperStatus.Success)
                    if (binding.profileRvTravel.adapter == null) {
                        travelAdapter = TravelAdapter(baseActivity, { viewModel.action.onAddTravelClick() }) { position, item ->
                            viewModel.action.onTravelClick(position, item)
                        }
                        binding.profileRvTravel.adapter = travelAdapter
                    }
                    travelAdapter?.submitList(it.list)
                }
                is ProfileEvents.SetStatLoading       -> binding.profileCwStat.setStatus(ContentWrapper.WrapperStatus.Loading)
                is ProfileEvents.SetStatFailure       -> binding.profileCwStat.setStatus(ContentWrapper.WrapperStatus.Failure(it.message))
                is ProfileEvents.UpdateStatList       -> {
                    binding.profileCwStat.setStatus(ContentWrapper.WrapperStatus.Success)
                    if (binding.profileRvStat.adapter == null) {
                        statAdapter = StatAdapter(viewModel.app)
                        binding.profileRvStat.adapter = statAdapter
                    }
                    statAdapter?.submitList(it.list)
                }
                is ProfileEvents.LaunchIntent         -> baseActivity.startActivity(it.intent)
                is ProfileEvents.EditContact          -> {
                    editContactBottomSheet = EditContactBottomSheet(viewModel, it.contentItem) { contactItem ->
                        viewModel.action.onConfirmEditContactClick(contactItem)
                    }.also { sheet ->
                        sheet.show(childFragmentManager, EditContactBottomSheet::class.simpleName)
                    }
                }
                is ProfileEvents.EditContactError     -> editContactBottomSheet?.showError(it.error)
                is ProfileEvents.EditContactComplete  -> editContactBottomSheet?.dismiss()
            }
        }
    }
}
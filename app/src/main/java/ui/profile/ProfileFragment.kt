package ui.profile

import adapter.StatAdapter
import adapter.TravelAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.xodus.traveli.R
import com.xodus.traveli.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment
import ui.base.ContentWrapper
import ui.dialog.EditBottomSheet

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileEvents, ProfileAction, ProfileViewModel>(R.layout.fragment_profile) {
    private var editBottomSheet: EditBottomSheet? = null
    private val args by navArgs<ProfileFragmentArgs>()
    private var travelAdapter: TravelAdapter? = null
    private var statAdapter: StatAdapter? = null

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
                is ProfileEvents.NavBack              -> findNavController().popBackStack()
                is ProfileEvents.NavSetting           -> findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment())
                is ProfileEvents.NavTravel            -> Unit
                is ProfileEvents.NavAddTravel         -> Unit
                is ProfileEvents.NavTransactionList   -> findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToTransactionFragment(it.balance))
                is ProfileEvents.SetBioLoading        -> binding.profileCwBio.setStatus(ContentWrapper.WrapperStatus.Loading)
                is ProfileEvents.SetBioFailure        -> binding.profileCwBio.setStatus(ContentWrapper.WrapperStatus.Failure(it.message))
                is ProfileEvents.UpdateBio            -> {
                    binding.apply {
                        profileCwBio.setStatus(ContentWrapper.WrapperStatus.Success)
                        profileTvBio.text = it.bio
                        //                        profileBtnBack.isVisible = it.isMe.not()
                        //                        profileBtnSetting.isVisible = it.isMe
                        //                        profileCwBalance.isVisible = it.isMe
                        //                        profileIvCover.load(it.user.cover)
                        //                        profileIvAvatar.load(it.user.avatar)
                        //                        profileTvName.text = it.user.name
                        //                        profileTvHometown.text = it.user.hometown
                        //                        profileTvBio.text = it.user.bio
                        //                        it.contact.apply {
                        //                            profileBtnEmail.backgroundTintList = ContextCompat.getColorStateList(requireContext(), if (email.value == null) R.color.md_grey_500 else email.color)
                        //                            profileBtnPhone.backgroundTintList = ContextCompat.getColorStateList(requireContext(), if (phone.value == null) R.color.md_grey_500 else phone.color)
                        //                            profileBtnTwitter.backgroundTintList = ContextCompat.getColorStateList(requireContext(), if (twitter.value == null) R.color.md_grey_500 else twitter.color)
                        //                            profileBtnInstagram.backgroundTintList = ContextCompat.getColorStateList(requireContext(), if (instagram.value == null) R.color.md_grey_500 else instagram.color)
                        //                            profileBtnWebsite.backgroundTintList = ContextCompat.getColorStateList(requireContext(), if (website.value == null) R.color.md_grey_500 else website.color)
                        //                        }
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
                    editBottomSheet = EditBottomSheet(baseActivity, it.contentItem.icon, it.contentItem.color, it.contentItem.title, it.contentItem.value) { value ->
                        viewModel.action.onConfirmEditContactClick(it.contentItem.copy(value = value))
                    }.also { sheet ->
                        sheet.show(childFragmentManager, EditBottomSheet::class.simpleName)
                    }
                }
                is ProfileEvents.EditContactError     -> editBottomSheet?.showError(it.error)
                is ProfileEvents.EditContactComplete  -> editBottomSheet?.dismiss()
            }
        }
    }
}
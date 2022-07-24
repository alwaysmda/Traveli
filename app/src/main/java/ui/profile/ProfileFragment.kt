package ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileEvents, ProfileAction, ProfileViewModel>(R.layout.fragment_profile) {
    private var editContactBottomSheet: EditContactBottomSheet? = null
    private val args by navArgs<ProfileFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: ProfileViewModel by viewModels()
        initialize(vm)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeToEvents()
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerViews()
        viewModel.action.onStart(args.userId)
    }

    private fun observeToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.event.collect {
            when (it) {
                is ProfileEvents.NavSetting          -> Unit
                is ProfileEvents.NavTravel           -> Unit
                is ProfileEvents.UpdateUser          -> Unit
                is ProfileEvents.UpdateTravelList    -> Unit
                is ProfileEvents.UpdateStatList      -> Unit
                is ProfileEvents.LaunchIntent        -> Unit
                is ProfileEvents.EditContact         -> {
                    editContactBottomSheet = EditContactBottomSheet(viewModel, it.title, it.content) { content ->
                        viewModel.action.onConfirmEditContactClick(content)
                    }.also { sheet ->
                        sheet.show(childFragmentManager, EditContactBottomSheet::class.simpleName)
                    }
                }
                is ProfileEvents.EditContactError    -> editContactBottomSheet?.showError(it.error)
                is ProfileEvents.EditContactComplete -> editContactBottomSheet?.dismiss()
            }
        }
    }

    private fun setUpRecyclerViews() {
        //        trendingTravelAdapter = SquareTravelAdapter(baseActivity)
        //        subBannerAdapter = TravelAdapter(baseActivity)
        //        countryAdapter = CountryAdapter(baseActivity)
        //        newTravelAdapter = SquareTravelAdapter(baseActivity)
        //        binding.apply {
        //            rvTrendingTravel.adapter = trendingTravelAdapter
        //            rvNewTravel.adapter = newTravelAdapter
        //            rvSubBanner.adapter = subBannerAdapter
        //            rvCountries.adapter = countryAdapter
        //
        //        }
    }
}
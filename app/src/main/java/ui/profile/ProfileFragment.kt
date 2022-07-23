package ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment
import util.extension.log

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileEvents, ProfileAction, ProfileViewModel>(R.layout.fragment_profile) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: ProfileViewModel by viewModels()
        initialize(vm)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeToEvents()
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerViews()
        arguments?.let {
            viewModel.action.onStart(ProfileFragmentArgs.fromBundle(it).userId)
        }
    }

    private fun observeToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.event.collect {
            log("FLOW:OBSERVE")
            when (it) {
                is ProfileEvents.Loading -> {
                }
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
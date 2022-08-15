package ui.home

import adapter.CountryAdapter
import adapter.HorizontalSquareTravelAdapter
import adapter.TravelAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.xodus.traveli.R
import com.xodus.traveli.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment
import ui.base.ContentWrapper
import util.extension.log
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeEvents, HomeAction, HomeViewModel>(R.layout.fragment_home) {

    private lateinit var trendingTravelAdapter: HorizontalSquareTravelAdapter
    private lateinit var newTravelAdapter: HorizontalSquareTravelAdapter
    private lateinit var subBannerAdapter: TravelAdapter
    private lateinit var countryAdapter: CountryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: HomeViewModel by viewModels()
        initialize(vm)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var exit = false
        baseActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (exit) {
                    baseActivity.finishAffinity()
                } else {
                    exit = true
                    snack(viewModel.app.m.pressBackAgainToExit)
                }

                Timer().schedule(object : TimerTask() {
                    override fun run() {
                        exit = false
                    }
                }, 2500)
            }
        })
        observeToEvents()
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerViews()
        viewModel.action.onStart()
        setUpActions()


    }


    private fun observeToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {

        viewModel.event.collect {
            log("FLOW:OBSERVE")
            when (it) {
                is HomeEvents.TrendingTravelUpdate    -> {
                    binding.apply {
                        cwTrending.setStatus(ContentWrapper.WrapperStatus.Success)
                    }
                    trendingTravelAdapter.submitList(it.travelPreviews)
                }
                is HomeEvents.BannerUpdate            -> {
                    binding.apply {
                        binding.ivBanner.load(it.banner.banner.image)
                        tvBannerName.text = it.banner.banner.name
                        subBannerAdapter.submitList(it.banner.subBanner)
                        cwBanner.setStatus(ContentWrapper.WrapperStatus.Success)
                    }

                }
                is HomeEvents.CountriesUpdate         -> {
                    binding.cwCountry.setStatus(ContentWrapper.WrapperStatus.Success)
                    countryAdapter.submitList(it.countries)
                }
                is HomeEvents.NewTravelUpdate         -> {
                    binding.cwNewTravel.setStatus(ContentWrapper.WrapperStatus.Success)
                    newTravelAdapter.submitList(it.travelPreviews)

                }
                //loadings
                is HomeEvents.Loading                 -> {
                }
                is HomeEvents.NewTravelLoading        -> {
                    binding.apply {
                        binding.cwNewTravel.setStatus(ContentWrapper.WrapperStatus.Loading)
                    }
                }
                is HomeEvents.BannerLoading           -> {
                    binding.apply {
                        cwBanner.setStatus(ContentWrapper.WrapperStatus.Loading)

                    }
                }
                is HomeEvents.CountriesLoading        -> {
                    binding.apply {
                        cwCountry.setStatus(ContentWrapper.WrapperStatus.Loading)
                    }
                }
                is HomeEvents.TrendingTravelLoading   -> {
                    binding.apply {
                        cwTrending.setStatus(ContentWrapper.WrapperStatus.Loading)
                    }

                }
                //errors
                is HomeEvents.OnError                 -> {
                }
                is HomeEvents.TrendingTravelError     -> {
                    binding.apply {
                        cwTrending.setStatus(ContentWrapper.WrapperStatus.Failure(it.message))
                    }
                }
                is HomeEvents.BannerError             -> {
                    binding.apply {
                        cwBanner.setStatus(ContentWrapper.WrapperStatus.Failure(it.message))

                    }
                }
                is HomeEvents.CountriesError          -> {
                    binding.apply {
                        cwCountry.setStatus(ContentWrapper.WrapperStatus.Failure(it.message))
                    }
                }
                is HomeEvents.NewTravelError          -> {
                    binding.apply {
                        cwNewTravel.setStatus(ContentWrapper.WrapperStatus.Failure(it.message))
                    }

                }
                is HomeEvents.NavToSearch             -> findNavController().navigate(it.direction)
                is HomeEvents.NavToTravelDetail       -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTravelDetailFragment(it.travelPreview))
                is HomeEvents.NavToTravelListFragment -> findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToTravelListFragment(it.type))
            }
        }


    }

    private fun setUpActions() {
        binding.apply {
            // btnBannerRetry.setOnClickListener { viewModel.action.onGetBannerRetry() }
//            btnTrendingRetry.setOnClickListener { viewModel.action.onGetTrendingRetry() }
//            btnNewTravelRetry.setOnClickListener { viewModel.action.onGetNewRetry() }
//            btnCountriesRetry.setOnClickListener { viewModel.action.onGetCountriesRetry() }
            //   btnSearch.setOnClickListener { viewModel.action.onSearchClick() }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

    }


    private fun setUpRecyclerViews() {
        trendingTravelAdapter = HorizontalSquareTravelAdapter(baseActivity) { travel, pos ->
            viewModel.action.onTravelItemClick(travel, pos)
        }
        subBannerAdapter = TravelAdapter(baseActivity) { position, travel -> //TODO
            viewModel.action.onTravelItemClick(travel, position)
        }
        countryAdapter = CountryAdapter(baseActivity)
        newTravelAdapter = HorizontalSquareTravelAdapter(baseActivity) { travel, pos ->
            viewModel.action.onTravelItemClick(travel, pos)
        }
        binding.apply {
            rvCountries.adapter = countryAdapter
            rvTrendingTravel.adapter = trendingTravelAdapter
            rvNewTravel.adapter = newTravelAdapter
            rvSubBanner.adapter = subBannerAdapter


        }
    }

}
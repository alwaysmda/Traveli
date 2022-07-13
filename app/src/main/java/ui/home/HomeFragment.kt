package ui.home

import adapter.CountryAdapter
import adapter.SubBannerAdapter
import adapter.TravelAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment
import util.extension.log

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeEvents, HomeAction, HomeViewModel>(R.layout.fragment_home) {

    private lateinit var trendingTravelAdapter: TravelAdapter
    private lateinit var newTravelAdapter: TravelAdapter
    private lateinit var subBannerAdapter: SubBannerAdapter
    private lateinit var countryAdapter: CountryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: HomeViewModel by viewModels()
        initialize(vm)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeToEvents()
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerViews()
        binding.root.post { viewModel.action.onStart() }
        setUpActions()

    }


    private fun observeToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {

        viewModel.event.collect {
                log("FLOW:OBSERVE")
                when (it) {
                    is HomeEvents.TrendingTravelUpdate  -> {
                        binding.apply {
                            rvTrendingTravel.isVisible = true
                            trendingLoading.isVisible = false
                            trendingProgress.isVisible = false
                            tvTrendingError.isVisible = false
                            btnTrendingRetry.isVisible = false
                        }
                        trendingTravelAdapter.submitList(it.travels)
                    }
                    is HomeEvents.BannerUpdate          -> {
                        binding.apply {
                            binding.bannerLoading.isVisible = false
                            Picasso.get().load(it.banner.banner.image).into(binding.ivBanner)
                            tvBannerName.text = it.banner.banner.name
                            subBannerAdapter.submitList(it.banner.subBanner)
                        }

                    }
                    is HomeEvents.CountriesUpdate       -> {
                        binding.countriesLoading.isVisible = false
                        countryAdapter.submitList(it.countries)
                    }
                    is HomeEvents.NewTravelUpdate       -> {
                        binding.newTravelLoading.isVisible = false
                        binding.rvNewTravel.isVisible = true
                        newTravelAdapter.submitList(it.travels)
                    }
                    //loadings
                    is HomeEvents.Loading               -> {
                    }
                    is HomeEvents.NewTravelLoading      -> {
                        binding.apply {
                            newTravelLoading.isVisible = true
                            newTravelProgress.isVisible = true
                            tvNewTravelError.isVisible = false
                            btnNewTravelRetry.isVisible = false
                            rvNewTravel.isVisible = false
                        }
                    }
                    is HomeEvents.BannerLoading         -> {
                        binding.apply {
                            bannerLoading.isVisible = true; bannerProgress.isVisible = true; tvBannerError.isVisible = false;
                            btnBannerRetry.isVisible = false;

                        }
                    }
                    is HomeEvents.CountriesLoading      -> {
                        binding.apply {
                            countriesLoading.isVisible = true
                            countriesProgress.isVisible = true
                            tvCountriesError.isVisible = false
                            btnCountriesRetry.isVisible = false
                        }
                    }
                    is HomeEvents.TrendingTravelLoading -> {
                        binding.apply {
                            trendingLoading.isVisible = true
                            trendingProgress.isVisible = true
                            tvTrendingError.isVisible = false
                            btnTrendingRetry.isVisible = false
                            rvTrendingTravel.isVisible = false
                        }

                    }
                    //errors
                    is HomeEvents.OnError               -> {
                    }
                    is HomeEvents.TrendingTravelError   -> {
                        binding.apply {
                            trendingLoading.isVisible = true
                            trendingProgress.isVisible = false
                            tvTrendingError.isVisible = true
                            btnTrendingRetry.isVisible = true
                            rvTrendingTravel.isVisible = false
                            tvTrendingError.text = it.message
                        }
                    }
                    is HomeEvents.BannerError           -> {
                        binding.apply {
                            bannerLoading.isVisible = true; bannerProgress.isVisible = false; tvBannerError.isVisible = true;
                            btnBannerRetry.isVisible = true;tvBannerError.text = it.message

                        }
                    }
                    is HomeEvents.CountriesError        -> {
                        binding.apply {
                            countriesLoading.isVisible = true
                            countriesProgress.isVisible = false
                            tvCountriesError.isVisible = true
                            btnCountriesRetry.isVisible = true
                            tvCountriesError.text = it.message
                        }
                    }
                    is HomeEvents.NewTravelError        -> {
                        binding.apply {
                            newTravelLoading.isVisible = true
                            newTravelProgress.isVisible = false
                            tvNewTravelError.isVisible = true
                            btnNewTravelRetry.isVisible = true
                            tvNewTravelError.text = it.message
                            rvNewTravel.isVisible = false
                        }

                    }
                    is HomeEvents.NavToSearch           -> findNavController().navigate(it.direction)
                }
            }


    }

    private fun setUpActions(){
        binding.apply {
            btnBannerRetry.setOnClickListener { viewModel.action.onGetBannerRetry() }
            btnTrendingRetry.setOnClickListener { viewModel.action.onGetTrendingRetry() }
            btnNewTravelRetry.setOnClickListener { viewModel.action.onGetNewRetry() }
            btnCountriesRetry.setOnClickListener { viewModel.action.onGetCountriesRetry() }
            btnSearch.setOnClickListener { viewModel.action.onSearchClick() }
        }
    }


    private fun setUpRecyclerViews() {
        trendingTravelAdapter = TravelAdapter(baseActivity)
        subBannerAdapter = SubBannerAdapter(baseActivity)
        countryAdapter = CountryAdapter(baseActivity)
        newTravelAdapter = TravelAdapter(baseActivity)
        binding.apply {
            rvTrendingTravel.adapter = trendingTravelAdapter
            rvNewTravel.adapter = newTravelAdapter
            rvSubBanner.adapter = subBannerAdapter
            rvCountries.adapter = countryAdapter

        }
    }

}
package ui.home

import adapter.CountryAdapter
import adapter.SubBannerAdapter
import adapter.TravelAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.squareup.picasso.Picasso
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeEvents, HomeAction, HomeViewModel>(R.layout.fragment_home) {

    private lateinit var trendingTravelAdapter: TravelAdapter
    private lateinit var newTravelAdapter:TravelAdapter
    private lateinit var subBannerAdapter: SubBannerAdapter
    private lateinit var countryAdapter:CountryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: HomeViewModel by viewModels()
        initialize(vm)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerViews()
        observeToEvents()
        viewModel.action.onStart()

    }


    private fun observeToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
            viewModel.event.collect {
                when (it) {
                    is HomeEvents.TrendingTravelUpdate ->{
                        trendingTravelAdapter.submitList(it.travels)
                    }
                    is HomeEvents.BannerUpdate -> {
                        Picasso.get().load(it.banner.banner.image).into(binding.ivBanner)
                        binding.tvBannerName.text = it.banner.banner.name
                        subBannerAdapter.submitList(it.banner.subBanner)
                    }
                    is HomeEvents.CountriesUpdate      -> countryAdapter.submitList(it.countries)
                    is HomeEvents.NewTravelUpdate      -> newTravelAdapter.submitList(it.travels)
                    //loadings
                    is HomeEvents.Loading                 -> {}
                    is HomeEvents.NewTravelLoading -> {}
                    is HomeEvents.BannerLoading    -> {}
                    is HomeEvents.CountriesLoading -> {}
                    is HomeEvents.TrendingTravelLoading   -> {}
                    //errors
                    is HomeEvents.OnError                -> {}
                    is HomeEvents.TrendingTravelError  -> {}
                    is HomeEvents.BannerError          -> {}
                    is HomeEvents.CountriesError       -> {}
                    is HomeEvents.NewTravelError       -> {}


                }
            }
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
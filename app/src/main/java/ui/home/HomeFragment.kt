package ui.home

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
import kotlinx.coroutines.flow.collectLatest
import ui.base.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeEvents, HomeAction, HomeViewModel>(R.layout.fragment_home) {

    private lateinit var trendingTravelAdapter: TravelAdapter
    private lateinit var subBannerAdapter: SubBannerAdapter

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
                    is HomeEvents.OnError                -> {}
                    HomeEvents.OnLoading                 -> {}
                    is HomeEvents.OnTrendingTravelUpdate ->{
                        trendingTravelAdapter.submitList(it.travels)
                    }
                    is HomeEvents.OnBannerUpdate -> {
                        Picasso.get().load(it.banner.banner.image).into(binding.ivBanner)
                        binding.tvBannerName.text = it.banner.banner.name
                        subBannerAdapter.submitList(it.banner.subBanner)
                    }
                }
            }
        }

    }


    private fun setUpRecyclerViews() {
        trendingTravelAdapter = TravelAdapter(baseActivity)
        subBannerAdapter = SubBannerAdapter(baseActivity)
        binding.rvTrendingTravel.adapter = trendingTravelAdapter
        binding.rvSubBanner.adapter = subBannerAdapter
    }
}
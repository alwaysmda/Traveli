package ui.home

import adapter.TravelAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ui.base.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeEvents, HomeAction, HomeViewModel>(R.layout.fragment_home) {

    private lateinit var trendingTravelAdapter: TravelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm: HomeViewModel by viewModels()
        initialize(vm)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        viewModel.onStart()
        observeToEvents()
    }


    private fun observeToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        viewModel.event.collectLatest {
            when (it) {
                is HomeEvents.OnError                -> {}
                HomeEvents.OnLoading                 -> {}
                is HomeEvents.OnTrendingTravelUpdate -> trendingTravelAdapter.submitList(it.travels)
            }
        }
    }


    private fun setUpRecyclerView() {
        trendingTravelAdapter = TravelAdapter(baseActivity)
        binding.rvTrendingTravel.adapter = trendingTravelAdapter
    }
}
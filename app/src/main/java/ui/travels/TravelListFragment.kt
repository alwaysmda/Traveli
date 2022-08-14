package ui.travels

import adapter.VerticalSquareTravelAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.xodus.traveli.R
import com.xodus.traveli.databinding.FragmentTravelListBinding
import ui.base.BaseFragment

class TravelListFragment : BaseFragment<FragmentTravelListBinding, TravelListEvent, TravelListAction, TravelListViewModel>(R.layout.fragment_travel_list) {


    private lateinit var travelAdapter: VerticalSquareTravelAdapter
    private val args: TravelListFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: TravelListViewModel by viewModels()
        initialize(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeToEvents()
        viewModel.action.onStart(args.travelType)

    }


    private fun observeToEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.apply {
                event.collect {
                    when (it) {
                        is TravelListEvent.TravelListError  -> {}
                        TravelListEvent.TravelListLoading   -> {}
                        is TravelListEvent.TravelListUpdate -> travelAdapter.submitList(it.travelList)
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        travelAdapter = VerticalSquareTravelAdapter(baseActivity)
        binding.rvTravel.adapter = travelAdapter

    }


}
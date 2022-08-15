package ui.travels

import adapter.VerticalSquareTravelAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.xodus.traveli.R
import com.xodus.traveli.databinding.FragmentTravelListBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment
import ui.base.ContentWrapper

@AndroidEntryPoint
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
        viewModel.action.onStart(args.travelType)
        observeToEvents()
        setUpScrollListener()


    }


    private fun setUpScrollListener() {
        binding.rvTravel.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.computeVerticalScrollExtent() +
                    recyclerView.computeVerticalScrollOffset() >
                    recyclerView.computeVerticalScrollRange() - 100
                ) {
                    viewModel.action.paginate()
                }
            }

        })
    }

    private fun observeToEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.apply {
                event.collect {
                    when (it) {
                        is TravelListEvent.TravelListError  -> {
                            binding.cwRvTravel.setStatus(ContentWrapper.WrapperStatus.Failure(it.message))
                        }
                        TravelListEvent.TravelListLoading   -> {}
                        is TravelListEvent.TravelListUpdate -> {
                            binding.cwRvTravel.setStatus(ContentWrapper.WrapperStatus.Success)
                            travelAdapter.submitList(it.travelList)
                        }
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
package ui.search

import adapter.TravelAdapter
import adapter.UserAdapter
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchEvents, SearchActions, SearchViewModel>(R.layout.fragment_search) {

    private lateinit var userAdapter: UserAdapter
    private lateinit var travelAdapter: TravelAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: SearchViewModel by viewModels()
        initialize(viewModel)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerViews()
        observeToEvents()
        setUpAction()

    }

    private fun setUpAction() {
        binding.apply {
            edtSearch.addTextChangedListener { viewModel.action.onSearch(edtSearch.text.toString()) }

        }

    }

    private fun observeToEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.event.collect {
                when (it) {
                    is SearchEvents.UpdateUsers -> userAdapter.submitList(it.users)
                    is SearchEvents.UpdateTravel -> travelAdapter.submitList(it.travels)
                    is SearchEvents.UserError   -> {
                    }
                    is SearchEvents.UserLoading -> {
                    }
                }
            }
        }

    }

    private fun setUpRecyclerViews() {
        userAdapter = UserAdapter(baseActivity)
        travelAdapter = TravelAdapter(baseActivity)
        binding.apply {
            rvUser.adapter = userAdapter
            rvTravel.adapter = travelAdapter

        }

    }

}
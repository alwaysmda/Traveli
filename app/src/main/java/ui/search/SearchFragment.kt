package ui.search

import adapter.TravelAdapter
import adapter.UserAdapter
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment
import util.extension.changeChildFont


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
        setUpViews()
        viewModel.action.onStart()


    }

    private fun setUpAction() {
        binding.apply {
            edtSearch.addTextChangedListener { viewModel.action.onSearch(edtSearch.text.toString()) }
            searchTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    binding.apply {
                        progressBar.isVisible = false
                        tvUserError.isVisible = false
                        tvTravelError.isVisible = false
                    }

                    viewModel.action.onChangeTab(tab?.position ?: 0, edtSearch.text.toString())

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
            btnBack.setOnClickListener { viewModel.action.onBackPress() }
        }

    }

    private fun observeToEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.event.collect {
                when (it) {
                    is SearchEvents.UpdateUsers        -> {
                        userAdapter.submitList(it.users)
                        binding.progressBar.isVisible = false
                    }
                    is SearchEvents.UpdateTravel       -> {
                        travelAdapter.submitList(it.travelPreviews)
                        binding.progressBar.isVisible = false
                    }
                    is SearchEvents.UserError          -> {
                        binding.apply {
                            progressBar.isVisible = false
                            tvUserError.isVisible = true
                            tvUserError.text = it.message
                        }

                    }
                    is SearchEvents.UserLoading        -> {
                        binding.apply {
                            progressBar.isVisible = true
                            tvUserError.isVisible = false
                            tvTravelError.isVisible = false
                        }
                    }
                    is SearchEvents.RvTravelVisibility -> binding.rvTravel.isVisible = it.isVisible
                    is SearchEvents.RvUserVisibility   -> binding.rvUser.isVisible = it.isVisible
                    is SearchEvents.TravelError        -> {
                        binding.apply {
                            progressBar.isVisible = false
                            tvTravelError.isVisible = true
                            tvTravelError.text = it.message
                        }


                    }
                    is SearchEvents.TravelLoading      -> {
                        binding.apply {
                            progressBar.isVisible = true
                            tvTravelError.isVisible = false
                            tvUserError.isVisible = false
                        }
                    }

                    is SearchEvents.NavBack            -> findNavController().popBackStack()

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

    private fun setUpViews() {
        binding.searchTab.apply {
            addTab(newTab().setText(viewModel.app.m.user))
            addTab(newTab().setText(viewModel.app.m.travel))
            val vg = binding.searchTab.getChildAt(0) as ViewGroup
            val vgTab1 = vg.getChildAt(0) as ViewGroup
            val vgTab2 = vg.getChildAt(1) as ViewGroup
            vgTab1.changeChildFont(viewModel.app.m.fontBold!!)
            vgTab2.changeChildFont(viewModel.app.m.fontBold!!)


        }


    }

}
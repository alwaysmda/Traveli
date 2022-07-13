package ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment


@AndroidEntryPoint
class SearchFragment:BaseFragment<FragmentSearchBinding,SearchEvents,SearchActions,SearchViewModel>(R.layout.fragment_search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel:SearchViewModel by viewModels()
        initialize(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
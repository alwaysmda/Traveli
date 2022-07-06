package ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment

@AndroidEntryPoint
class HomeFragment:BaseFragment<FragmentHomeBinding,HomeEvents,HomeAction,HomeViewModel>(R.layout.fragment_home) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm:HomeViewModel by viewModels()
        initialize(vm)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
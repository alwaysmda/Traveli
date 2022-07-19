package ui.travelDetail


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.xodus.templatefive.R
import com.xodus.templatefive.databinding.FragmentTravelDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.base.BaseFragment

@AndroidEntryPoint
class TravelDetailFragment:BaseFragment<FragmentTravelDetailBinding,TravelDetailEvents,TravelDetailAction,TravelDetailViewModel>(R.layout.fragment_travel_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel:TravelDetailViewModel by viewModels()
        initialize(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
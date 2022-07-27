package ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xodus.traveli.R

class HomeTabFragment : Fragment(R.layout.fragment_tab) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController().navigate(HomeTabFragmentDirections.actionHomeTabFragmentToHomeFragment())
       
    }
}
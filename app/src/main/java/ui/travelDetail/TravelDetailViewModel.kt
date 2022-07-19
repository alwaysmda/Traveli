package ui.travelDetail

import dagger.hilt.android.lifecycle.HiltViewModel
import main.ApplicationClass
import ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class TravelDetailViewModel @Inject constructor(app:ApplicationClass):BaseViewModel<TravelDetailEvents,TravelDetailAction>(app),TravelDetailAction {

    override fun onStart() {

    }


}
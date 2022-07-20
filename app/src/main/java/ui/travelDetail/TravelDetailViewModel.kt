package ui.travelDetail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.usecase.travel.TravelUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import main.ApplicationClass
import ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class TravelDetailViewModel @Inject constructor(
    app: ApplicationClass,
    private val travelUseCases: TravelUseCases,
) : BaseViewModel<TravelDetailEvents, TravelDetailAction>(app), TravelDetailAction {

    override fun onStart() {
        getTravelDetail()
    }


    private fun getTravelDetail() {
        viewModelScope.launch {
            travelUseCases.getTravelDetail(0).onEach {
                when (it) {
                    is DataState.Failure -> _event.send(TravelDetailEvents.TravelDetailError(it.message))
                    DataState.Loading    -> _event.send(TravelDetailEvents.TravelDetailLoading)
                    is DataState.Success -> _event.send(TravelDetailEvents.UpdateTravelDetail(it.data))
                }

            }.launchIn(viewModelScope)
        }
    }


}
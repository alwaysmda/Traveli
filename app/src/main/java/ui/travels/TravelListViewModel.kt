package ui.travels

import androidx.lifecycle.viewModelScope
import data.remote.DataState
import domain.usecase.travel.TravelUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import main.ApplicationClass
import ui.base.BaseViewModel
import javax.inject.Inject

class TravelListViewModel @Inject constructor(app: ApplicationClass, private val travelUseCases: TravelUseCases) : BaseViewModel<TravelListEvent, TravelListAction>(app), TravelListAction {


    override fun onStart(type: String) {
        getTravelList(type)
    }

    private fun getTravelList(type: String) {
        travelUseCases.getTravelByTypeUseCase(type, 0).onEach {
            when (it) {
                is DataState.Failure -> _event.send(TravelListEvent.TravelListError(it.message))
                is DataState.Loading -> _event.send(TravelListEvent.TravelListLoading)
                is DataState.Success -> _event.send(TravelListEvent.TravelListUpdate(it.data))
            }

        }.launchIn(viewModelScope)

    }


}
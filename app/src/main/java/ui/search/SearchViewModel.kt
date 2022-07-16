package ui.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.usecase.travel.TravelUseCases
import domain.usecase.user.UserUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import main.ApplicationClass
import ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    app: ApplicationClass,
    private val userUseCases: UserUseCases,
    private val travelUseCases: TravelUseCases
) : BaseViewModel<SearchEvents, SearchActions>(app), SearchActions {

    private var job: Job? = null

    override fun onSearch(text: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(1000)
            userUseCases.getUser(text).onEach {
                when (it) {
                    is DataState.Failure -> _event.emit(SearchEvents.UserError(it.message))
                    is DataState.Loading -> _event.emit(SearchEvents.UserLoading)
                    is DataState.Success -> _event.emit(SearchEvents.UpdateUsers(it.data))
                }
            }.launchIn(viewModelScope)

            travelUseCases.getTravel().onEach {
                when (it) {
                    is DataState.Failure -> _event.emit(SearchEvents.TravelError(it.message))
                    DataState.Loading    -> _event.emit(SearchEvents.TravelLoading)
                    is DataState.Success -> _event.emit(SearchEvents.UpdateTravel(it.data))
                }
            }.launchIn(viewModelScope)

        }
    }

}
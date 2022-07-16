package ui.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.model.User
import domain.model.travel.Travel
import domain.usecase.travel.TravelUseCases
import domain.usecase.user.UserUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import main.ApplicationClass
import ui.base.BaseViewModel
import util.Constant.TRAVEL_TAB
import util.Constant.USER_TAB
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    app: ApplicationClass,
    private val userUseCases: UserUseCases,
    private val travelUseCases: TravelUseCases
) : BaseViewModel<SearchEvents, SearchActions>(app), SearchActions {

    //local
    private var job: Job? = null
    private var tabIndex = USER_TAB

    override fun onSearch(text: String) {
        search(text)
    }

    private fun search(text: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(1000)
            when (tabIndex) {
                0 -> userUseCases.getUser(text).onEach {
                    when (it) {
                        is DataState.Failure -> _event.emit(SearchEvents.UserError(it.message))
                        is DataState.Loading -> _event.emit(SearchEvents.UserLoading)
                        is DataState.Success -> {
                            _event.emit(SearchEvents.UpdateUsers(it.data))
                        }

                    }
                }.launchIn(viewModelScope)


                1 -> travelUseCases.getTravel().onEach {
                    when (it) {
                        is DataState.Failure -> _event.emit(SearchEvents.TravelError(it.message))
                        DataState.Loading    -> _event.emit(SearchEvents.TravelLoading)
                        is DataState.Success -> {
                            _event.emit(SearchEvents.UpdateTravel(it.data))
                        }


                    }
                }.launchIn(viewModelScope)


            }


        }
    }

    override fun onChangeTab(tabIndex: Int) {
        this.tabIndex = tabIndex
        viewModelScope.launch {
            when (tabIndex) {
                USER_TAB   -> {
                    _event.emit(SearchEvents.RvUserVisibility(true))
                    _event.emit(SearchEvents.RvTravelVisibility(false))

                }
                TRAVEL_TAB -> {

                    _event.emit(SearchEvents.RvUserVisibility(false))
                    _event.emit(SearchEvents.RvTravelVisibility(true))
                }
            }
        }

    }

}
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
    private var lastUserQuery = ""
    private var lastTravelQuery = ""
    private var users = listOf<User>()
    private var travels = listOf<Travel>()


    override fun onStart() {
        viewModelScope.launch {
            delay(500)
            _event.emit(SearchEvents.UpdateUsers(users))
            _event.emit(SearchEvents.UpdateTravel(travels))
        }
    }

    override fun onSearch(text: String) {
        search(text)
    }

    private fun search(text: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(1000)
            when (tabIndex) {
                USER_TAB   -> {
                    if (text == lastUserQuery) return@launch
                    userUseCases.getUser(text).onEach {

                        when (it) {
                            is DataState.Failure -> {
                                if (tabIndex == USER_TAB) _event.emit(SearchEvents.UserError(it.message))
                                users = listOf()
                                _event.emit(SearchEvents.UpdateUsers(users))
                            }
                            is DataState.Loading -> _event.emit(SearchEvents.UserLoading)
                            is DataState.Success -> {
                                lastUserQuery = text
                                users = it.data
                                _event.emit(SearchEvents.UpdateUsers(users))
                            }

                        }
                    }.launchIn(viewModelScope)
                }


                TRAVEL_TAB -> {
                    if (text == lastTravelQuery) return@launch
                    travelUseCases.getTravel().onEach {
                        when (it) {
                            is DataState.Failure -> if (tabIndex == TRAVEL_TAB) {
                                travels = listOf()
                                _event.emit(SearchEvents.TravelError(it.message))
                                _event.emit(SearchEvents.UpdateTravel(travels))
                            }
                            DataState.Loading    -> _event.emit(SearchEvents.TravelLoading)
                            is DataState.Success -> {
                                lastTravelQuery = text
                                _event.emit(SearchEvents.UpdateTravel(it.data))
                            }


                        }
                    }.launchIn(viewModelScope)
                }


            }


        }
    }

    override fun onChangeTab(tabIndex: Int, query: String) {
        this.tabIndex = tabIndex
        viewModelScope.launch {
            when (tabIndex) {
                USER_TAB   -> {
                    _event.emit(SearchEvents.RvUserVisibility(true))
                    _event.emit(SearchEvents.RvTravelVisibility(false))
                    if (query.isNotEmpty() && query != lastUserQuery) search(query)

                }
                TRAVEL_TAB -> {

                    _event.emit(SearchEvents.RvUserVisibility(false))
                    _event.emit(SearchEvents.RvTravelVisibility(true))
                    if (query.isNotEmpty() && query != lastTravelQuery) search(query)

                }
            }
        }


    }

}
package ui.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.model.TravelPreview
import domain.model.TravelPreview.Companion.cloned
import domain.model.UserPreview
import domain.model.UserPreview.Companion.cloned
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
import util.extension.log
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    app: ApplicationClass,
    private val userUseCases: UserUseCases,
    private val travelUseCases: TravelUseCases
) : BaseViewModel<SearchEvents, SearchActions>(app), SearchActions {
    var get = 0

    //local
    private var job: Job? = null
    private var tabIndex = USER_TAB
    private var lastUserQuery = ""
    private var lastTravelQuery = ""
    private var travelPage = 1
    private var userPage = 1
    private var isUserRequesting = false
    private var isTravelRequesting = false


    private var travelList = mutableListOf<TravelPreview>()
    private var userPreviewList = mutableListOf<UserPreview>()


    override fun onStart() {
        viewModelScope.launch {
            _event.send(SearchEvents.UpdateUsers(userPreviewList.cloned()))
            _event.send(SearchEvents.UpdateTravel(travelList.cloned()))
        }
    }

    override fun onSearch(text: String) {
        search(text)

    }


    override fun paginateTravelList() {
        if (isTravelRequesting) return
        isTravelRequesting = true
        viewModelScope.launch {
            travelUseCases.searchTravelsUseCase(lastTravelQuery, travelPage + 1).onEach { //TODO paginate and add query
                when (it) {
                    is DataState.Failure -> if (tabIndex == TRAVEL_TAB) {
                        isTravelRequesting = false
                        travelList.clear()
                        val clonedList = userPreviewList.cloned()
                        log("orig${userPreviewList.hashCode()}")
                        log("clone:${clonedList.hashCode()}")
                        _event.send(SearchEvents.TravelError(it.message))
                        _event.send(SearchEvents.UpdateTravel(travelList.cloned()))
                    }
                    DataState.Loading    -> {
                        _event.send(SearchEvents.TravelLoading)
                    }
                    is DataState.Success -> { //TODO update list
                        isTravelRequesting = false
                        travelList.addAll(it.data)
                        travelPage++
                        _event.send(SearchEvents.UpdateTravel(travelList.cloned()))
                    }


                }
            }.launchIn(viewModelScope)
        }
    }

    override fun paginateUserList() {
        if (isUserRequesting) return
        isUserRequesting = true
        viewModelScope.launch {
            userUseCases.searchUserUseCase(lastUserQuery, userPage + 1).onEach { //TODO paginate
                when (it) {
                    is DataState.Failure -> {
                        isUserRequesting = false
                        if (tabIndex == USER_TAB) _event.send(SearchEvents.UserError(it.message))
                        userPreviewList.clear()
                        _event.send(SearchEvents.UpdateUsers(userPreviewList.cloned()))
                    }
                    is DataState.Loading -> {
                        _event.send(SearchEvents.UserLoading)
                    }
                    is DataState.Success -> {
                        isUserRequesting = false
                        userPreviewList.addAll(it.data)
                        userPage++
                        _event.send(SearchEvents.UpdateUsers(userPreviewList.cloned()))
                    }

                }
            }.launchIn(viewModelScope)

        }
    }

    fun search(text: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(1000)
            when (tabIndex) {
                USER_TAB   -> {
                    if (text == lastUserQuery) return@launch
//                    userUseCases.searchUserUseCase(text, 1).collectLatest {
//
//                    }
                    userUseCases.searchUserUseCase(text, 1).onEach { //TODO paginate
                        when (it) {
                            is DataState.Failure -> {
                                if (tabIndex == USER_TAB) _event.send(SearchEvents.UserError(it.message))
                                userPreviewList.clear()
                                _event.send(SearchEvents.UpdateUsers(userPreviewList.cloned()))
                            }
                            is DataState.Loading -> _event.send(SearchEvents.UserLoading)
                            is DataState.Success -> {
                                lastUserQuery = text
                                userPreviewList.clear()
                                userPreviewList.addAll(it.data)
                                _event.send(SearchEvents.UpdateUsers(userPreviewList.cloned()))
                            }

                        }
                    }.launchIn(viewModelScope)
                }


                TRAVEL_TAB -> {
                    if (text == lastTravelQuery) return@launch

                    travelUseCases.searchTravelsUseCase(text, 1).onEach { //TODO paginate and add query
                        when (it) {
                            is DataState.Failure -> if (tabIndex == TRAVEL_TAB) {
                                travelList.clear()
                                _event.send(SearchEvents.TravelError(it.message))
                                _event.send(SearchEvents.UpdateTravel(travelList.cloned()))
                            }
                            DataState.Loading    -> _event.send(SearchEvents.TravelLoading)
                            is DataState.Success -> { //TODO update list
                                travelList.clear()
                                travelList.addAll(it.data.toMutableList())
                                lastTravelQuery = text
                                _event.send(SearchEvents.UpdateTravel(travelList.cloned()))
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
                    _event.send(SearchEvents.RvUserVisibility(true))
                    _event.send(SearchEvents.RvTravelVisibility(false))
                    if (query.isNotEmpty() && query != lastUserQuery) search(query)

                }
                TRAVEL_TAB -> {
                    _event.send(SearchEvents.RvUserVisibility(false))
                    _event.send(SearchEvents.RvTravelVisibility(true))
                    if (query.isNotEmpty() && query != lastTravelQuery) search(query)

                }
            }
        }


    }

    override fun onBackPress() {
        viewModelScope.launch {
            _event.send(SearchEvents.NavBack)
        }
    }

    override fun onUserItemClick(user: UserPreview, pos: Int) {
        viewModelScope.launch {
            _event.send(SearchEvents.NavUser(user))
        }
    }

}
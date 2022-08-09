package ui.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.model.TravelPreview
import domain.model.TravelPreview.Companion.cloned
import domain.model.TravelPreview.Companion.getLoadingItem
import domain.model.UserPreview
import domain.model.UserPreview.Companion.LOADING_ID
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
    private var travelPage = 1
    private var userPage = 1
    private var isUserRequesting = false
    private var isTravelRequesting = false


    private var travelList = mutableListOf<TravelPreview>()
    private var userPreviewList = mutableListOf<UserPreview>()
    private var userHasMore = true
    private var travelHasMore = true


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
                        val list = travelList.cloned()
                        list.add(TravelPreview.getFailureItem())
                        _event.send(SearchEvents.UpdateTravel(list))
                    }
                    DataState.Loading    -> {
                        //  _event.send(SearchEvents.TravelLoading)
                    }
                    is DataState.Success -> { //TODO update list
                        isTravelRequesting = false
                        travelList.addAll(it.data)
                        travelPage++
                        val list = travelList.cloned()
                        if (travelHasMore) {
                            list.add(getLoadingItem())
                        }
                        _event.send(SearchEvents.UpdateTravel(list))
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
                        val list = userPreviewList.cloned()
                        list.add(UserPreview.getFailureItem())
                        _event.send(SearchEvents.UpdateUsers(list))
                    }
                    is DataState.Loading -> {
                        //  _event.send(SearchEvents.UserLoading)
                    }
                    is DataState.Success -> {
                        isUserRequesting = false
                        userPreviewList.addAll(it.data)
                        val list = userPreviewList.cloned()
                        if (userHasMore) {
                            list.add(UserPreview(LOADING_ID, "", ""))
                        }
                        userPage++
                        _event.send(SearchEvents.UpdateUsers(list))
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
                                val list = userPreviewList.cloned()
                                if (userHasMore) {
                                    list.add(UserPreview(LOADING_ID, "", ""))
                                }
                                _event.send(SearchEvents.UpdateUsers(list))
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
                            is DataState.Success -> {
                                travelList.clear()
                                travelList.addAll(it.data.toMutableList())
                                lastTravelQuery = text
                                val list = travelList.cloned()
                                if (travelHasMore) {
                                    list.add(getLoadingItem())
                                }
                                _event.send(SearchEvents.UpdateTravel(list))
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
                    _event.send(SearchEvents.TabUser)
                    if (query.isNotEmpty() && query != lastUserQuery) search(query)

                }
                TRAVEL_TAB -> {
                    _event.send(SearchEvents.TabTravel)
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
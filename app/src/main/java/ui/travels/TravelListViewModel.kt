package ui.travels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.model.TravelPreview
import domain.model.TravelPreview.Companion.cloned
import domain.usecase.travel.TravelUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import main.ApplicationClass
import ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class TravelListViewModel @Inject constructor(app: ApplicationClass, private val travelUseCases: TravelUseCases) : BaseViewModel<TravelListEvent, TravelListAction>(app), TravelListAction {


    //local
    private var page = 0
    private var type = ""
    private var hasMore = true
    private var isPaginating = false
    private val travelList = arrayListOf<TravelPreview>()


    override fun onStart(type: String) {
        this.type = type
        if (travelList.isEmpty()) {
            getTravelList(type)
        } else {
            viewModelScope.launch {
                _event.send(TravelListEvent.TravelListUpdate(travelList.cloned()))
            }
        }

    }

    override fun paginate() {
        if (isPaginating.not() && hasMore) {
            isPaginating = true
            travelUseCases.getTravelByTypeUseCase(type, page + 1).onEach {
                when (it) {
                    is DataState.Failure -> {
                        val tempList = travelList.cloned()
                        tempList.add(TravelPreview.getFailureItem())
                        _event.send(TravelListEvent.TravelListUpdate(tempList))

                    }
                    DataState.Loading    -> {

                    }
                    is DataState.Success -> {
                        isPaginating = false
                        travelList.addAll(it.data)
                        val tempTravelList = travelList.cloned()
                        if (hasMore) {
                            tempTravelList.add(TravelPreview.getLoadingItem())
                        }
                        page++
                        _event.send(TravelListEvent.TravelListUpdate(tempTravelList))
                    }
                }
            }.launchIn(viewModelScope)
        }

    }

    override fun onTravelItemClick(travelPreview: TravelPreview, pos: Int) {
        viewModelScope.launch {
            _event.send(TravelListEvent.NavToTravelDetail(travelPreview))
        }
    }


    private fun getTravelList(type: String) {
        travelUseCases.getTravelByTypeUseCase(type, 0).onEach {
            when (it) {
                is DataState.Failure -> _event.send(TravelListEvent.TravelListError(it.message))
                is DataState.Loading -> _event.send(TravelListEvent.TravelListLoading)
                is DataState.Success -> {
                    travelList.addAll(it.data)
                    val tempList = travelList.cloned()
                    if (hasMore) {
                        tempList.add(TravelPreview.getLoadingItem())
                    }
                    _event.send(TravelListEvent.TravelListUpdate(tempList))
                }
            }

        }.launchIn(viewModelScope)

    }


}
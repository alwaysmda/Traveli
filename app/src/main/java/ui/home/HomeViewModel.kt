package ui.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.usecase.travel.TravelUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import main.ApplicationClass
import ui.base.BaseEvent
import ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: ApplicationClass,
    private val travelUseCases: TravelUseCases
) : BaseViewModel<HomeEvents, HomeAction>(app),HomeAction {


     override fun onStart() {

        getTrendingTravel()
        getBanner()
    }







    private fun getTrendingTravel(){
        travelUseCases.getTravel(true).onEach {
            when(it){
                is DataState.Failure -> _event.emit(HomeEvents.OnError(it.message))
                DataState.Loading    -> _event.emit(HomeEvents.OnLoading)
                is DataState.Success -> _event.emit(HomeEvents.OnTrendingTravelUpdate(it.data))
            }

        }.launchIn(viewModelScope)
    }

    private fun getBanner(){
        travelUseCases.getBanner().onEach {
            when(it){
                is DataState.Failure -> _event.emit(HomeEvents.OnError(it.message))
                DataState.Loading    -> _event.emit(HomeEvents.OnLoading)
                is DataState.Success -> _event.emit(HomeEvents.OnBannerUpdate(it.data))
            }
        }.launchIn(viewModelScope)
    }







}
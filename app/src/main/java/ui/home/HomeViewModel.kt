package ui.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.usecase.country.CountryUseCases
import domain.usecase.travel.TravelUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import main.ApplicationClass
import ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: ApplicationClass,
    private val travelUseCases: TravelUseCases,
    private val countryUseCases: CountryUseCases
) : BaseViewModel<HomeEvents, HomeAction>(app), HomeAction {


    override fun onStart() {
        getTrendingTravel()
        getNewTravel()
        getBanner()
        getCountries()
    }

    override fun onGetBannerRetry() {
        getBanner()
    }

    override fun onGetTrendingRetry() {
        getTrendingTravel()
    }

    override fun onGetNewRetry() {
        getNewTravel()
    }

    override fun onGetCountriesRetry() {
        getCountries()
    }
/*
API
base/user/me
base/travels/banner
base/travels/trending
base/travels/new
base/countries

///
font
search button
text padding
title margin
new list
separate requests
corner radius (sub banner)
handle error / retry
remove add tab
update repo and usecases
* */

    private fun getTrendingTravel() {
        travelUseCases.getTrending().onEach {
            when (it) {
                is DataState.Failure -> _event.emit(HomeEvents.TrendingTravelError(it.message))
                is DataState.Loading -> _event.emit(HomeEvents.TrendingTravelLoading)
                is DataState.Success -> _event.emit(HomeEvents.TrendingTravelUpdate(it.data))
            }

        }.launchIn(viewModelScope)
    }

    private fun getNewTravel(){
        travelUseCases.getNewTravel().onEach {
            when(it){
                is DataState.Failure -> _event.emit(HomeEvents.NewTravelError(it.message))
                is DataState.Loading    -> _event.emit(HomeEvents.NewTravelLoading)
                is DataState.Success ->  _event.emit(HomeEvents.NewTravelUpdate(it.data))

            }

        }.launchIn(viewModelScope)
    }

    private fun getBanner() {
        travelUseCases.getBanner().onEach {
            when (it) {
                is DataState.Failure -> _event.emit(HomeEvents.BannerError(it.message))
                DataState.Loading    -> _event.emit(HomeEvents.BannerLoading)
                is DataState.Success -> _event.emit(HomeEvents.BannerUpdate(it.data))


            }
        }.launchIn(viewModelScope)
    }

    private fun getCountries() {
        countryUseCases.getCountry().onEach {
            when (it) {
                is DataState.Failure -> _event.emit(HomeEvents.CountriesError(it.message))
                is DataState.Loading    -> _event.emit(HomeEvents.Loading)
                is DataState.Success -> _event.emit(HomeEvents.CountriesUpdate(it.data))
            }
        }.launchIn(viewModelScope)
    }


}
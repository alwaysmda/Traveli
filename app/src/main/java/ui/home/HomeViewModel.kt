package ui.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.usecase.country.CountryUseCases
import domain.usecase.travel.TravelUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import main.ApplicationClass
import ui.base.BaseViewModel
import util.extension.log
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

    override fun onSearchClick() {
        viewModelScope.launch {
            _event.send(HomeEvents.NavToSearch(HomeFragmentDirections.actionHomeFragmentToSearchFragment()))
        }
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
                is DataState.Failure -> _event.send(HomeEvents.TrendingTravelError(it.message))
                is DataState.Loading -> _event.send(HomeEvents.TrendingTravelLoading)
                is DataState.Success -> _event.send(HomeEvents.TrendingTravelUpdate(it.data))
            }

        }.launchIn(viewModelScope)
    }

    private fun getNewTravel(){
        travelUseCases.getNewTravel().onEach {
            when(it){
                is DataState.Failure -> _event.send(HomeEvents.NewTravelError(it.message))
                is DataState.Loading    -> _event.send(HomeEvents.NewTravelLoading)
                is DataState.Success -> {
                    log("FLOW:travel")
                    _event.send(HomeEvents.NewTravelUpdate(it.data))
                }

            }

        }.launchIn(viewModelScope)
    }

    private fun getBanner() {
        travelUseCases.getBanner().onEach {
            when (it) {
                is DataState.Failure -> _event.send(HomeEvents.BannerError(it.message))
                DataState.Loading    -> _event.send(HomeEvents.BannerLoading)
                is DataState.Success -> {
                    log("FLOW:banner")
                    _event.send(HomeEvents.BannerUpdate(it.data))
                }


            }
        }.launchIn(viewModelScope)
    }

    private fun getCountries() {
        countryUseCases.getCountry().onEach {
            when (it) {
                is DataState.Failure -> _event.send(HomeEvents.CountriesError(it.message))
                is DataState.Loading    -> _event.send(HomeEvents.Loading)
                is DataState.Success -> _event.send(HomeEvents.CountriesUpdate(it.data))
            }
        }.launchIn(viewModelScope)
    }


}
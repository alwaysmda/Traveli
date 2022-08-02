package ui.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.model.TravelPreview
import domain.usecase.country.CountryUseCases
import domain.usecase.travel.TravelUseCases
import domain.usecase.user.UserUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import main.ApplicationClass
import ui.base.BaseViewModel
import util.Constant
import util.extension.log
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: ApplicationClass,
    private val userUseCases: UserUseCases,
    private val travelUseCases: TravelUseCases,
    private val countryUseCases: CountryUseCases
) : BaseViewModel<HomeEvents, HomeAction>(app), HomeAction {
    override fun onStart() {
        app.prefManager.setPref(Constant.PREF_TOKEN, "Token")
        if (app.user == null) getMe() else getData()

    }

    override fun onGetBannerRetry() { // onGetCountriesRetryClick
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

    override fun onTravelItemClick(travelPreview: TravelPreview, pos: Int) {
        viewModelScope.launch {
            _event.send(HomeEvents.NavToTravelDetail(travelPreview)) //TODO pass travel
        }
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
    private fun getMe() {
        userUseCases.getMeUseCase().onEach {
            when (it) {
                is DataState.Failure -> getData()
                is DataState.Loading -> _event.send(HomeEvents.BannerLoading)
                is DataState.Success -> {
                    app.user = it.data
                    getData()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getData() {
        getCountries()
        getNewTravel()
        getBanner()
        getTrendingTravel()
    }

    private fun getTrendingTravel() {
        travelUseCases.getTrendingListUseCase().onEach {
            when (it) {
                is DataState.Failure -> _event.send(HomeEvents.TrendingTravelError(it.message))
                is DataState.Loading -> _event.send(HomeEvents.TrendingTravelLoading)
                is DataState.Success -> _event.send(HomeEvents.TrendingTravelUpdate(it.data))
            }
        }.launchIn(viewModelScope)
    }

    private fun getNewTravel() {
        travelUseCases.getNewTravelListUseCase().onEach {
            when (it) {
                is DataState.Failure -> _event.send(HomeEvents.NewTravelError(it.message))
                is DataState.Loading -> _event.send(HomeEvents.NewTravelLoading)
                is DataState.Success -> {
                    log("FLOW:travel")
                    _event.send(HomeEvents.NewTravelUpdate(it.data))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getBanner() {
        travelUseCases.getBannerUseCase().onEach {
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
                is DataState.Loading -> _event.send(HomeEvents.Loading)
                is DataState.Success -> _event.send(HomeEvents.CountriesUpdate(it.data))
            }
        }.launchIn(viewModelScope)
    }
}
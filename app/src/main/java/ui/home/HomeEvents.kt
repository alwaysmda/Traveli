package ui.home

import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import domain.model.Country
import domain.model.travel.Banner
import domain.model.travel.Travel
import ui.base.BaseEvent

sealed class HomeEvents : BaseEvent() {
    object Loading : HomeEvents()
    data class TrendingTravelUpdate(val travels: List<Travel>) : HomeEvents()
    data class NewTravelUpdate(val travels: List<Travel>) : HomeEvents()
    data class CountriesUpdate(val countries: List<Country>) : HomeEvents()
    data class BannerUpdate(val banner: Banner) : HomeEvents()
    data class NavToSearch(val direction:NavDirections):HomeEvents()


    //errors
    data class OnError(val message: String) : HomeEvents()
    data class TrendingTravelError(val message: String) : HomeEvents()
    data class NewTravelError(val message: String) : HomeEvents()
    data class CountriesError(val message: String) : HomeEvents()
    data class BannerError(val message: String) : HomeEvents()

    //loadings
    object TrendingTravelLoading:HomeEvents()
    object NewTravelLoading:HomeEvents()
    object CountriesLoading : HomeEvents()
    object BannerLoading : HomeEvents()

}
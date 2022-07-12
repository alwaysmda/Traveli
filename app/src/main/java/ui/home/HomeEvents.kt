package ui.home

import domain.model.Country
import domain.model.travel.Banner
import domain.model.travel.Travel
import ui.base.BaseEvent

sealed class HomeEvents : BaseEvent() {
    object OnLoading : HomeEvents()
    data class OnTrendingTravelUpdate(val travels: List<Travel>) : HomeEvents()
    data class OnCountriesUpdate(val countries:List<Country>): HomeEvents()
    data class OnBannerUpdate(val banner:Banner):HomeEvents()
    data class OnError(val message: String) : HomeEvents()



}
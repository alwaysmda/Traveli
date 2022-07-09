package ui.home

import domain.model.travel.Travel
import ui.base.BaseEvent

sealed class HomeEvents : BaseEvent() {
    object OnLoading : HomeEvents()
    data class OnTrendingTravelUpdate(val travels: List<Travel>) : HomeEvents()
    data class OnError(val message: String) : HomeEvents()


}
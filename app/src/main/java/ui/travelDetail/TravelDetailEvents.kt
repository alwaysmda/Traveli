package ui.travelDetail

import domain.model.TravelDetail
import ui.base.BaseEvent

sealed class TravelDetailEvents : BaseEvent() {
    data class UpdateTravelDetail(val travelDetails: List<TravelDetail>) : TravelDetailEvents()

    //Errors
    data class TravelDetailError(val message: String) : TravelDetailEvents()

    //Loadings
    object TravelDetailLoading : TravelDetailEvents()

    data class OpenUrl(val url: String) : TravelDetailEvents()
    object NavBack : TravelDetailEvents()


}

package ui.travelDetail

import domain.model.travel.TravelDetail
import ui.base.BaseEvent

sealed class TravelDetailEvents : BaseEvent() {
    data class UpdateTravelDetail(val travelDetail: TravelDetail) : TravelDetailEvents()

    //Errors
    data class TravelDetailError(val message: String) : TravelDetailEvents()

    //Loadings
    object TravelDetailLoading : TravelDetailEvents()

}

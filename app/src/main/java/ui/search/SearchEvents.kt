package ui.search

import domain.model.User
import domain.model.travel.TravelPreview
import ui.base.BaseEvent

sealed class SearchEvents : BaseEvent() {

    data class UpdateUsers(val users: List<User>) : SearchEvents()
    data class UpdateTravel(val travelPreviews: List<TravelPreview>) : SearchEvents()

    //loadings
    object UserLoading : SearchEvents()
    object TravelLoading : SearchEvents()

    //errors
    data class UserError(val message: String) : SearchEvents()
    data class TravelError(val message: String) : SearchEvents()

    //visibility
    data class RvUserVisibility(val isVisible: Boolean) : SearchEvents()
    data class RvTravelVisibility(val isVisible: Boolean) : SearchEvents()

    object NavBack : SearchEvents()

}

package ui.search


import domain.model.TravelPreview
import domain.model.UserPreview
import ui.base.BaseEvent


sealed class SearchEvents : BaseEvent() {
    data class UpdateUsers(val userPreviews: List<UserPreview>) : SearchEvents()
    data class UpdateTravel(val travels: List<TravelPreview>) : SearchEvents()


    //loadings
    object UserLoading : SearchEvents()
    object TravelLoading : SearchEvents()

    //errors
    data class UserError(val message: String) : SearchEvents()
    data class TravelError(val message: String) : SearchEvents()


    data class UserNotFound(val message: String) : SearchEvents()
    data class TravelNotFound(val message: String) : SearchEvents()

    object TabUser : SearchEvents()
    object TabTravel : SearchEvents()

    object NavBack : SearchEvents()
    data class NavUser(val user: UserPreview) : SearchEvents()
    data class NavTravel(val travel: TravelPreview) : SearchEvents()

}

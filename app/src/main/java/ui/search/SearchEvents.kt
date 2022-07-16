package ui.search

import domain.model.User
import domain.model.travel.Travel
import ui.base.BaseEvent

sealed class SearchEvents : BaseEvent() {

    data class UpdateUsers(val users: List<User>) : SearchEvents()

    //loadings
    object UserLoading : SearchEvents()

    //errors
    data class UserError(val message: String) : SearchEvents()

}

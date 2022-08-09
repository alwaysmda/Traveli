package ui.search

import domain.model.TravelPreview
import domain.model.UserPreview
import ui.base.BaseAction

interface SearchActions : BaseAction {
    fun onStart()
    fun onSearch(text: String)
    fun paginateTravelList()
    fun paginateUserList()
    fun onChangeTab(tab: Int, query: String)
    fun onBackPress()
    fun onUserItemClick(user: UserPreview, pos: Int)
    fun onTravelItemClick(travel: TravelPreview, pos: Int)

}
package ui.travelDetail

import ui.base.BaseAction

interface TravelDetailAction : BaseAction {
    fun onStart()
    fun onLinkClick(url: String)

}
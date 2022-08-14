package ui.travels

import ui.base.BaseAction

interface TravelListAction : BaseAction {
    fun onStart(type: String)

}
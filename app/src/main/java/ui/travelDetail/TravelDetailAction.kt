package ui.travelDetail

import domain.model.TravelPreview
import ui.base.BaseAction

interface TravelDetailAction : BaseAction {
    fun onStart(travelPreview: TravelPreview)
    fun onLinkClick(url: String)
    fun onBackPress()

}
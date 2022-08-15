package ui.travels

import domain.model.TravelPreview
import ui.base.BaseAction

interface TravelListAction : BaseAction {
    fun onStart(type: String)
    fun paginate()
    fun onTravelItemClick(travelPreview: TravelPreview, pos: Int)

}
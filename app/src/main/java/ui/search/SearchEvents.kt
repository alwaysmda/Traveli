package ui.search

import domain.model.travel.Travel
import ui.base.BaseEvent

sealed class SearchEvents:BaseEvent(){
class NavTravel(travel: Travel) : SearchEvents()
}

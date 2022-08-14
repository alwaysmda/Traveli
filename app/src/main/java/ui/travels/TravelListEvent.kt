package ui.travels

import domain.model.TravelPreview
import ui.base.BaseEvent

sealed class TravelListEvent : BaseEvent() {

    data class TravelListUpdate(val travelList: List<TravelPreview>) : TravelListEvent()
    object TravelListLoading : TravelListEvent()

    //error
    data class TravelListError(val message: String) : TravelListEvent()

}
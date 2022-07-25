package ui.home

import domain.model.TravelPreview
import ui.base.BaseAction

interface HomeAction : BaseAction {
    fun onStart()
    fun onSearchClick()
    fun onGetBannerRetry()
    fun onGetTrendingRetry()
    fun onGetNewRetry()
    fun onGetCountriesRetry()
    fun onTravelItemClick(travelPreview: TravelPreview, pos: Int)


}
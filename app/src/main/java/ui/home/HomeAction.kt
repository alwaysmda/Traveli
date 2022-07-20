package ui.home

import domain.model.travel.Travel
import ui.base.BaseAction

interface HomeAction : BaseAction {
    fun onStart()
    fun onSearchClick()
    fun onGetBannerRetry()
    fun onGetTrendingRetry()
    fun onGetNewRetry()
    fun onGetCountriesRetry()
    fun onTravelItemClick(travel: Travel, pos: Int)


}
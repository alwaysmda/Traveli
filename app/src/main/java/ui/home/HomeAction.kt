package ui.home

import ui.base.BaseAction

interface HomeAction:BaseAction {
    fun onStart()
    fun onGetBannerRetry()
    fun onGetTrendingRetry()
    fun onGetNewRetry()
    fun onGetCountriesRetry()


}
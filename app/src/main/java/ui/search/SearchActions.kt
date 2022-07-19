package ui.search

import ui.base.BaseAction

interface SearchActions : BaseAction {
    fun onStart()
    fun onSearch(text: String)
    fun onChangeTab(tab: Int,query:String)
    fun onBackPress()

}
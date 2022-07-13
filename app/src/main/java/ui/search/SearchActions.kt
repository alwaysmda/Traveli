package ui.search

import ui.base.BaseAction

interface SearchActions:BaseAction {
    fun onSearch(text:String)
}
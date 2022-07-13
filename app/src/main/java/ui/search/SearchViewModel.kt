package ui.search

import dagger.hilt.android.lifecycle.HiltViewModel
import main.ApplicationClass
import ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    app:ApplicationClass
):BaseViewModel<SearchEvents,SearchActions>(app),SearchActions {

}
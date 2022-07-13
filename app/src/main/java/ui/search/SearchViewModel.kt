package ui.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import main.ApplicationClass
import ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    app:ApplicationClass
):BaseViewModel<SearchEvents,SearchActions>(app),SearchActions {

    private var job:Job? = null

    override fun onSearch(text: String) {
        job?.cancel()
        job = viewModelScope.launch {

        }
    }

}
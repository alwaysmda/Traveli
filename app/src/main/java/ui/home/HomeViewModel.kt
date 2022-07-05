package ui.home

import dagger.hilt.android.lifecycle.HiltViewModel
import main.ApplicationClass
import ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(app:ApplicationClass):BaseViewModel<HomeEvents,HomeAction>(app) {
}
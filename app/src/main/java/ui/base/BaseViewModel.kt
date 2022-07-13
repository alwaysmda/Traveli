package ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import main.ApplicationClass

open class BaseViewModel<E : BaseEvent, A : BaseAction>(
    val app: ApplicationClass,
) : ViewModel(), BaseAction {
    val action: A = this as A
    protected var _event = MutableSharedFlow<E>()
    val event = _event.asSharedFlow()
    var isFirstStart = true

}



open class BaseEvent
interface BaseAction



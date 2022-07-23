package ui.profile

import ui.base.BaseEvent

sealed class ProfileEvents : BaseEvent() {
    object Loading : ProfileEvents()
}
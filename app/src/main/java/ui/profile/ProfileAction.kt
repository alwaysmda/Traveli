package ui.profile

import ui.base.BaseAction

interface ProfileAction : BaseAction {
    fun onStart(userId: Long)
}
package ui.profile

import ui.base.BaseAction

interface ProfileAction : BaseAction {
    fun onStart(userId: Long)
    fun onSettingClick()
    fun onBalanceClick()
    fun onTravelClick()
    fun onPhoneClick()
    fun onEmailClick()
    fun onTwitterClick()
    fun onInstagramClick()
    fun onWebsiteClick()
    fun onConfirmEditContactClick(content: String?)
}
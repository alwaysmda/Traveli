package ui.profile

import domain.model.ContactItem
import domain.model.TravelPreview
import ui.base.BaseAction

interface ProfileAction : BaseAction {
    fun onStart(userId: Long)
    fun onSettingClick()
    fun onBalanceClick()
    fun onTravelClick(position: Int, item: TravelPreview)
    fun onAddTravelClick()
    fun onPhoneClick()
    fun onEmailClick()
    fun onTwitterClick()
    fun onInstagramClick()
    fun onWebsiteClick()
    fun onConfirmEditContactClick(contactItem: ContactItem)
    fun onRetryBalanceClick()
    fun onRetryTravelListClick()
    fun onRetryStatListClick()
}
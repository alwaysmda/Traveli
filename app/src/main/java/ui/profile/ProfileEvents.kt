package ui.profile

import android.content.Intent
import domain.model.Stat
import domain.model.UserPreview
import domain.model.travel.Travel
import ui.base.BaseEvent

sealed class ProfileEvents : BaseEvent() {
    object NavSetting : ProfileEvents()
    class NavTravel(val travel: Travel) : ProfileEvents()
    class UpdateUser(val userPreview: UserPreview) : ProfileEvents()
    class UpdateTravelList(val list: List<Travel>) : ProfileEvents()
    class UpdateStatList(val list: List<Stat>) : ProfileEvents()
    class LaunchIntent(val intent: Intent) : ProfileEvents()
    class EditContact(val title: String, val content: String?) : ProfileEvents()
    class EditContactError(val error: String) : ProfileEvents()
    object EditContactComplete : ProfileEvents()
}
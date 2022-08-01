package ui.profile

import android.content.Intent
import domain.model.Balance
import domain.model.ContactItem
import domain.model.Stat
import domain.model.TravelPreview
import ui.base.BaseEvent

sealed class ProfileEvents : BaseEvent() {
    //General
    class Snack(val message: String) : ProfileEvents()

    //Nav
    object NavBack : ProfileEvents()
    object NavSetting : ProfileEvents()
    class NavTransactionList(val balance: Balance) : ProfileEvents()
    object NavAddTravel : ProfileEvents()
    class NavTravel(val travel: TravelPreview) : ProfileEvents()

    //Bio
    object SetBioLoading : ProfileEvents()
    class SetBioFailure(val message: String) : ProfileEvents()
    class UpdateBio(val bio: String?) : ProfileEvents()

    //Balance
    object SetBalanceLoading : ProfileEvents()
    class SetBalanceFailure(val message: String) : ProfileEvents()
    class UpdateBalance(val balance: String) : ProfileEvents()

    //Travel
    object SetTravelListLoading : ProfileEvents()
    class SetTravelListFailure(val message: String) : ProfileEvents()
    class UpdateTravelList(val list: List<TravelPreview>) : ProfileEvents()

    //Stat
    object SetStatLoading : ProfileEvents()
    class SetStatFailure(val message: String) : ProfileEvents()
    class UpdateStatList(val list: List<Stat>) : ProfileEvents()

    //
    class LaunchIntent(val intent: Intent) : ProfileEvents()
    class EditContact(val contentItem: ContactItem) : ProfileEvents()
    class EditContactError(val error: String) : ProfileEvents()
    object EditContactComplete : ProfileEvents()
}
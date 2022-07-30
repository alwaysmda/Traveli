package ui.profile_edit

import domain.model.User
import ui.base.BaseEvent

sealed class ProfileEditEvents : BaseEvent() {
    //General
    class Snack(val message: String) : ProfileEditEvents()
    class ShowLoading(val show: Boolean) : ProfileEditEvents()

    //Nav
    object NavBack : ProfileEditEvents()

    //
    class PickImage(val aspectRatioX: Int, val aspectRatioY: Int) : ProfileEditEvents()
    class UpdateCover(val coverUrl: String) : ProfileEditEvents()
    class UpdateAvatar(val avatarUrl: String) : ProfileEditEvents()
    class UpdateInfo(val user: User) : ProfileEditEvents()
    class EditContent(val title: String, val value: String?, val multiline: Boolean) : ProfileEditEvents()
    class EditContentError(val error: String) : ProfileEditEvents()
    object EditContentComplete : ProfileEditEvents()
    class ShowDialog(
        val icon: Int?,
        val iconColor: Int?,
        val title: String,
        val content: String,
        val positive: String?,
        val onPositive: () -> Unit = {},
        val negative: String?,
        val onNegative: () -> Unit = {},
    ) : ProfileEditEvents()
}
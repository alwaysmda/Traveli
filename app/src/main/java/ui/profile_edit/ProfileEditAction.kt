package ui.profile_edit

import ui.base.BaseAction

interface ProfileEditAction : BaseAction {
    fun onStart()
    fun onBackClick()
    fun onEditCoverClick()
    fun onEditAvatarClick()
    fun onImageSelect(filePath: String?)
    fun onEditNameClick()
    fun onEditHometownClick()
    fun onEditBioClick()
    fun onConfirmEditClick(value: String?)
    fun onLogoutClick()
    fun onDeleteAccountClick()
}
package ui.profile_edit

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.usecase.user.UserUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import main.ApplicationClass
import ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    app: ApplicationClass,
    private val userUseCases: UserUseCases,
) : BaseViewModel<ProfileEditEvents, ProfileEditAction>(app), ProfileEditAction {
    private var editType = ProfileEditType.Cover

    private enum class ProfileEditType {
        Cover,
        Avatar,
        Name,
        Hometown,
        Bio,
    }

    override fun onStart() {
        viewModelScope.launch {
            app.user?.let {
                _event.send(ProfileEditEvents.UpdateCover(it.cover))
                _event.send(ProfileEditEvents.UpdateAvatar(it.avatar))
                _event.send(ProfileEditEvents.UpdateInfo(it))
            } ?: kotlin.run {
                _event.send(ProfileEditEvents.Snack(app.m.pleaseLoginToDoThisAction))
                _event.send(ProfileEditEvents.NavBack)
            }
        }
    }

    override fun onBackClick() {
        viewModelScope.launch {
            _event.send(ProfileEditEvents.NavBack)
        }
    }

    override fun onEditCoverClick() {
        viewModelScope.launch {
            editType = ProfileEditType.Cover
            _event.send(ProfileEditEvents.PickImage(2, 1))
        }
    }

    override fun onEditAvatarClick() {
        viewModelScope.launch {
            editType = ProfileEditType.Avatar
            _event.send(ProfileEditEvents.PickImage(1, 1))
        }
    }

    override fun onImageSelect(filePath: String?) {
        when (editType) {
            ProfileEditType.Cover  -> {
                userUseCases.updateCoverUseCase(filePath).onEach {
                    when (it) {
                        is DataState.Loading -> _event.send(ProfileEditEvents.ShowLoading(true))
                        is DataState.Failure -> {
                            _event.send(ProfileEditEvents.ShowLoading(false))
                            _event.send(ProfileEditEvents.Snack(it.message))
                        }
                        is DataState.Success -> {
                            _event.send(ProfileEditEvents.ShowLoading(false))
                            _event.send(ProfileEditEvents.UpdateCover(it.data.cover))
                            app.user = it.data
                        }
                    }
                }.launchIn(viewModelScope)
            }
            ProfileEditType.Avatar -> {
                userUseCases.updateAvatarUseCase(filePath).onEach {
                    when (it) {
                        is DataState.Loading -> _event.send(ProfileEditEvents.ShowLoading(true))
                        is DataState.Failure -> {
                            _event.send(ProfileEditEvents.ShowLoading(false))
                            _event.send(ProfileEditEvents.Snack(it.message))
                        }
                        is DataState.Success -> {
                            _event.send(ProfileEditEvents.ShowLoading(false))
                            _event.send(ProfileEditEvents.UpdateAvatar(it.data.avatar))
                            app.user = it.data
                        }
                    }
                }.launchIn(viewModelScope)
            }
            else                   -> Unit
        }
    }

    override fun onEditNameClick() {
        viewModelScope.launch {
            editType = ProfileEditType.Name
            _event.send(ProfileEditEvents.EditContent(app.m.name, app.user?.name, false))
        }
    }

    override fun onEditHometownClick() {
        viewModelScope.launch {
            editType = ProfileEditType.Hometown
            //todo
        }
    }

    override fun onEditBioClick() {
        viewModelScope.launch {
            editType = ProfileEditType.Bio
            _event.send(ProfileEditEvents.EditContent(app.m.bio, app.user?.bio, true))
        }
    }

    override fun onConfirmEditClick(value: String?) {
        app.user?.let { u ->
            val user = when (editType) {
                ProfileEditType.Name     -> u.copy(name = value)
                ProfileEditType.Hometown -> u
                ProfileEditType.Bio      -> u.copy(bio = value)
                else                     -> u
            }
            userUseCases.updateUserInfoUseCase(user).onEach {
                when (it) {
                    is DataState.Loading -> _event.send(ProfileEditEvents.ShowLoading(true))
                    is DataState.Failure -> {
                        _event.send(ProfileEditEvents.ShowLoading(false))
                        _event.send(ProfileEditEvents.Snack(it.message))
                    }
                    is DataState.Success -> {
                        _event.send(ProfileEditEvents.EditContentComplete)
                        _event.send(ProfileEditEvents.ShowLoading(false))
                        _event.send(ProfileEditEvents.UpdateInfo(it.data))
                        app.user = it.data
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun onLogoutClick() {
        viewModelScope.launch {
            _event.send(ProfileEditEvents.ShowDialog(null, null, app.m.logout, app.m.logoutDesc, app.m.logout, {
                userUseCases.logoutUseCase()
                viewModelScope.launch {
                    _event.send(ProfileEditEvents.NavBack)
                }
            }, app.m.cancel))
        }
    }

    override fun onDeleteAccountClick() {
        viewModelScope.launch {
            _event.send(ProfileEditEvents.ShowDialog(null, null, app.m.deleteAccount, app.m.deleteAccountDesc, app.m.cancel, {}, app.m.deleteAccount, {
                userUseCases.deleteAccountUseCase().onEach {
                    when (it) {
                        is DataState.Loading -> _event.send(ProfileEditEvents.ShowLoading(true))
                        is DataState.Failure -> {
                            _event.send(ProfileEditEvents.ShowLoading(false))
                            _event.send(ProfileEditEvents.Snack(it.message))
                        }
                        is DataState.Success -> {
                            _event.send(ProfileEditEvents.ShowLoading(false))
                            _event.send(ProfileEditEvents.NavBack)
                        }
                    }
                }.launchIn(viewModelScope)
            }))
        }
    }
}
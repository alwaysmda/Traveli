package ui.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.usecase.user.UserUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import main.ApplicationClass
import ui.base.BaseViewModel
import util.extension.log
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    app: ApplicationClass,
    private val userUseCases: UserUseCases,
) : BaseViewModel<ProfileEvents, ProfileAction>(app), ProfileAction {
    private var id = 0L
    override fun onStart(userId: Long) {
        id = if (userId == 0L) {
            app.userPreview?.id ?: 0L
        } else {
            userId
        }

        if (id == 0L) {
            //Register
        } else {
            //Get Data
        }
        getUserStat()
    }

    override fun onSettingClick() {
        viewModelScope.launch {
            _event.send(ProfileEvents.EditContact("Edit Twitter Address", null))
        }
    }

    override fun onBalanceClick() {
    }

    override fun onTravelClick() {
    }

    override fun onPhoneClick() {
    }

    override fun onEmailClick() {
    }

    override fun onTwitterClick() {
    }

    override fun onInstagramClick() {
    }

    override fun onWebsiteClick() {
    }

    override fun onConfirmEditContactClick(content: String?) {
    }

    private fun getUserStat() {
        userUseCases.getUserStat(id).onEach {
            when (it) {
                is DataState.Loading -> Unit
                is DataState.Failure -> Unit
                is DataState.Success -> {
                    it.data.forEach { stat ->
                        log("Stat : $stat")
                    }
                }
            }
        }.launchIn(viewModelScope)
        viewModelScope.launch {
        }
    }
}
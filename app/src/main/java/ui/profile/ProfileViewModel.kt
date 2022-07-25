package ui.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.model.Stat
import domain.model.travel.Travel
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
    private val travelList = arrayListOf<Travel>()
    private val statList = arrayListOf<Stat>()
    override fun onStart(userId: Long) {
        viewModelScope.launch {
            app.user?.let {
                _event.send(ProfileEvents.UpdateUser(it))
                getUserStat(it.id)
                getUserTravelList(it.id)
            } ?: kotlin.run {
                //Register
            }
        }
    }

    override fun onSettingClick() {
        viewModelScope.launch {
            _event.send(ProfileEvents.EditContact("Edit Twitter Address", null))
        }
    }

    override fun onBalanceClick() {
        viewModelScope.launch {
            _event.send(ProfileEvents.NavSetting)
        }
    }

    override fun onTravelClick() {
        viewModelScope.launch {
        }
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

    private fun getUserStat(id: Long) {
        userUseCases.getUserStat(id).onEach {
            when (it) {
                is DataState.Loading -> Unit
                is DataState.Failure -> {
                }
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

    private fun getUserTravelList(id: Long) {
        //        userUseCases.getUserTravelList(id).onEach {
        //            when (it) {
        //                is DataState.Loading -> Unit
        //                is DataState.Failure -> Unit
        //                is DataState.Success -> {
        //                    it.data.forEach { stat ->
        //                        log("Stat : $stat")
        //                    }
        //                }
        //            }
        //        }.launchIn(viewModelScope)
        //        viewModelScope.launch {
        //        }
    }
}
package ui.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.model.*
import domain.usecase.transaction.TransactionUseCases
import domain.usecase.user.UserUseCases
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import main.ApplicationClass
import ui.base.BaseViewModel
import util.Constant
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    app: ApplicationClass,
    private val userUseCases: UserUseCases,
    private val transactionUseCases: TransactionUseCases,
) : BaseViewModel<ProfileEvents, ProfileAction>(app), ProfileAction {
    private var id: Long = 0L
    private var user: User? = null
    private var transactionData: DataTransaction? = null
    private val travelList = arrayListOf<TravelPreview>()
    private val statList = arrayListOf<Stat>()
    private val isMe get() = app.user?.id == id

    override fun onStart(userId: Long) {
        viewModelScope.launch {
            if (userId == 0L) {
                //Self
                app.user?.let {
                    id = it.id
                    user = it
                    _event.send(ProfileEvents.UpdateUser(it, true))
                    getTransactionList()
                    getUserTravelList()
                    getUserStat()
                } ?: kotlin.run {
                    //Register
                    //todo remove
                    app.prefManager.setPref(Constant.PREF_TOKEN, "Token")
                }
            } else {
                //Others
                id = userId
                getUser()
                getUserTravelList()
                getUserStat()
            }
        }
    }

    override fun onSettingClick() {
        viewModelScope.launch {
            _event.send(ProfileEvents.NavSetting)
        }
    }

    override fun onBalanceClick() {
        viewModelScope.launch {
            transactionData?.let {
                _event.send(ProfileEvents.NavTransactionList(it))
            }
        }
    }

    override fun onTravelClick(position: Int, item: TravelPreview) {
        viewModelScope.launch {
            _event.send(ProfileEvents.NavTravel(item))
        }
    }

    override fun onAddTravelClick() {
        viewModelScope.launch {
            _event.send(ProfileEvents.NavAddTravel)
        }
    }

    override fun onPhoneClick() {
        viewModelScope.launch {
            if (isMe) {
                user?.contact?.phone?.let {
                    _event.send(ProfileEvents.EditContact(it))
                }
            } else {
                user?.contact?.phone?.value?.let {
                    Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$it")
                    }
                }
            }
        }
    }

    override fun onEmailClick() {
        viewModelScope.launch {
            if (isMe) {
                user?.contact?.email?.let {
                    _event.send(ProfileEvents.EditContact(it))
                }
            } else {
                user?.contact?.email?.value?.let {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, it)
                        putExtra(Intent.EXTRA_SUBJECT, app.m.fromTraveli)
                    }
                    _event.send(ProfileEvents.LaunchIntent(intent))
                }
            }
        }
    }

    override fun onTwitterClick() {
        viewModelScope.launch {
            if (isMe) {
                user?.contact?.twitter?.let {
                    _event.send(ProfileEvents.EditContact(it))
                }
            } else {
                user?.contact?.twitter?.value?.let {
                    val intent = try {
                        // get the Twitter app if possible
                        app.packageManager.getPackageInfo("com.twitter.android", 0)
                        Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=$it")).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                    } catch (e: Exception) {
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/$it"))
                    }
                }
            }
        }
    }

    override fun onInstagramClick() {
        viewModelScope.launch {
            if (isMe) {
                user?.contact?.instagram?.let {
                    _event.send(ProfileEvents.EditContact(it))
                }
            } else {
                user?.contact?.instagram?.value?.let {
                    val intent = try {
                        Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/$it")).apply { setPackage("com.instagram.android") }
                    } catch (e: ActivityNotFoundException) {
                        Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/$it"))
                    }
                    _event.send(ProfileEvents.LaunchIntent(intent))
                }
            }
        }
    }

    override fun onWebsiteClick() {
        viewModelScope.launch {
            if (isMe) {
                user?.contact?.website?.let {
                    _event.send(ProfileEvents.EditContact(it))
                }
            } else {
                user?.contact?.website?.value?.let {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    _event.send(ProfileEvents.LaunchIntent(intent))
                }
            }
        }
    }

    override fun onConfirmEditContactClick(contactItem: ContactItem) {
        userUseCases.updateContactUseCase(contactItem).onEach {
            when (it) {
                is DataState.Loading -> Unit
                is DataState.Failure -> _event.send(ProfileEvents.EditContactError(it.message))
                is DataState.Success -> {
                    user = it.data
                    _event.send(ProfileEvents.UpdateUser(it.data, it.data.id == app.user?.id))
                    _event.send(ProfileEvents.EditContactComplete)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onRetryBalanceClick() {
        getTransactionList()
    }

    override fun onRetryTravelListClick() {
        getUserTravelList()
    }

    override fun onRetryStatListClick() {
        getUserStat()
    }

    private fun getUser() {
        userUseCases.getUserUseCase(id).onEach {
            when (it) {
                is DataState.Loading -> _event.send(ProfileEvents.SetUserLoading)
                is DataState.Failure -> _event.send(ProfileEvents.SetUserFailure(it.message))
                is DataState.Success -> {
                    user = it.data
                    _event.send(ProfileEvents.UpdateUser(it.data, it.data.id == app.user?.id))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTransactionList() {
        transactionUseCases.getTransactionListUseCase(1).onEach {
            when (it) {
                is DataState.Loading -> _event.send(ProfileEvents.SetBalanceLoading)
                is DataState.Failure -> _event.send(ProfileEvents.SetBalanceFailure(it.message))
                is DataState.Success -> {
                    transactionData = it.data
                    _event.send(ProfileEvents.UpdateBalance(it.data.balanceString))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUserTravelList() {
        userUseCases.getUserTravelListUseCase(id, 1).onEach {
            when (it) {
                is DataState.Loading -> _event.send(ProfileEvents.SetTravelListLoading)
                is DataState.Failure -> _event.send(ProfileEvents.SetTravelListFailure(it.message))
                is DataState.Success -> {
                    travelList.clear()
                    travelList.addAll(it.data.subList(0, minOf(it.data.size, 4)))
                    if (user?.id == app.user?.id) {
                        travelList.add(TravelPreview.getAddItem())
                    }
                    _event.send(ProfileEvents.UpdateTravelList(travelList))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUserStat() {
        userUseCases.getUserStatUseCase(id).onEach {
            when (it) {
                is DataState.Loading -> _event.send(ProfileEvents.SetStatLoading)
                is DataState.Failure -> _event.send(ProfileEvents.SetStatFailure(it.message))
                is DataState.Success -> {
                    statList.clear()
                    statList.addAll(it.data)
                    _event.send(ProfileEvents.UpdateStatList(it.data))
                }
            }
        }.launchIn(viewModelScope)
        viewModelScope.launch {
        }
    }
}
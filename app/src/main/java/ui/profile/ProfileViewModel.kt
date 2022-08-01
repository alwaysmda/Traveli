package ui.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.xodus.traveli.R
import dagger.hilt.android.lifecycle.HiltViewModel
import data.remote.DataState
import domain.model.*
import domain.usecase.transaction.TransactionUseCases
import domain.usecase.user.UserUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import main.ApplicationClass
import ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    app: ApplicationClass,
    private val userUseCases: UserUseCases,
    private val transactionUseCases: TransactionUseCases,
) : BaseViewModel<ProfileEvents, ProfileAction>(app), ProfileAction {
    //Bind
    var coverUrl = MutableStateFlow("")
    var backVisibility = MutableStateFlow(false)
    var settingVisibility = MutableStateFlow(false)
    var avatarUrl = MutableStateFlow("")
    var nameText = MutableStateFlow("")
    var hometownText = MutableStateFlow("")
    var emailColor = MutableStateFlow(ContextCompat.getColor(app, R.color.md_grey_500))
    var phoneColor = MutableStateFlow(ContextCompat.getColor(app, R.color.md_grey_500))
    var twitterColor = MutableStateFlow(ContextCompat.getColor(app, R.color.md_grey_500))
    var instagramColor = MutableStateFlow(ContextCompat.getColor(app, R.color.md_grey_500))
    var websiteColor = MutableStateFlow(ContextCompat.getColor(app, R.color.md_grey_500))

    //Local
    private var user: User? = null
    private var balance: Balance? = null
    private val travelList = arrayListOf<TravelPreview>()
    private val statList = arrayListOf<Stat>()
    private val isMe get() = (app.user != null) && (app.user?.id == user?.id)

    override fun onStart(userId: Long) {
        viewModelScope.launch {
            if (isFirstStart) {
                isFirstStart = false
                if (userId == 0L) {
                    //Self
                    app.user?.let {
                        updateUser(it)
                        getBalance()
                        getUserTravelList(it.id)
                        getUserStat(it.id)
                    } ?: kotlin.run {
                        //Register
                    }
                } else {
                    //Others
                    getUser(userId)
                    getUserTravelList(userId)
                    getUserStat(userId)
                }
            } else {
                if (userId == 0L) {
                    //Self
                    app.user?.let {
                        updateUser(it)
                        balance?.balanceString?.let { value ->
                            _event.send(ProfileEvents.UpdateBalance(value))
                        } ?: kotlin.run {
                            getBalance()
                        }
                        if (travelList.isEmpty()) {
                            getUserTravelList(it.id)
                        } else {
                            _event.send(ProfileEvents.UpdateTravelList(travelList))
                        }
                        if (statList.isEmpty()) {
                            getUserStat(it.id)
                        } else {
                            _event.send(ProfileEvents.UpdateStatList(statList))
                        }
                    } ?: kotlin.run {
                        //Register
                    }
                } else {
                    //Others
                    user?.let {
                        updateUser(it)
                    } ?: kotlin.run {
                        getUser(userId)
                    }
                    if (travelList.isEmpty()) {
                        getUserTravelList(userId)
                    } else {
                        _event.send(ProfileEvents.UpdateTravelList(travelList))
                    }
                    if (statList.isEmpty()) {
                        getUserStat(userId)
                    } else {
                        _event.send(ProfileEvents.UpdateStatList(statList))
                    }
                }
            }
        }
    }

    override fun onBackClick() {
        viewModelScope.launch {
            _event.send(ProfileEvents.NavBack)
        }
    }

    override fun onSettingClick() {
        viewModelScope.launch {
            _event.send(ProfileEvents.NavSetting)
        }
    }

    override fun onBalanceClick() {
        viewModelScope.launch {
            balance?.let {
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
                    _event.send(ProfileEvents.LaunchIntent(intent))
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
                    updateUser(it.data)
                    _event.send(ProfileEvents.EditContactComplete)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onRetryBalanceClick() {
        getBalance()
    }

    override fun onRetryTravelListClick() {
        user?.let {
            getUserTravelList(it.id)
        }
    }

    override fun onRetryStatListClick() {
        user?.let {
            getUserStat(it.id)
        }
    }

    private fun getUser(userId: Long) {
        userUseCases.getUserUseCase(userId).onEach {
            when (it) {
                is DataState.Loading -> _event.send(ProfileEvents.SetBioLoading)
                is DataState.Failure -> _event.send(ProfileEvents.SetBioFailure(it.message))
                is DataState.Success -> {
                    updateUser(it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getBalance() {
        transactionUseCases.getBalanceUseCase().onEach {
            when (it) {
                is DataState.Loading -> _event.send(ProfileEvents.SetBalanceLoading)
                is DataState.Failure -> _event.send(ProfileEvents.SetBalanceFailure(it.message))
                is DataState.Success -> {
                    balance = it.data
                    _event.send(ProfileEvents.UpdateBalance(it.data.balanceString))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUserTravelList(userId: Long) {
        userUseCases.getUserTravelListUseCase(userId, 1).onEach {
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

    private fun getUserStat(userId: Long) {
        userUseCases.getUserStatUseCase(userId).onEach {
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

    private fun updateUser(u: User) {
        user = u
        val isMe = u.id == app.user?.id
        coverUrl.value = u.cover
        backVisibility.value = isMe.not()
        settingVisibility.value = isMe
        avatarUrl.value = u.avatar
        nameText.value = u.name ?: ""
        hometownText.value = u.hometown
        viewModelScope.launch {
            _event.send(ProfileEvents.UpdateBio(u.bio))
        }
        //Contact
        emailColor.value = ContextCompat.getColor(app, if (u.contact.email.value == null) R.color.md_grey_500 else u.contact.email.color)
        phoneColor.value = ContextCompat.getColor(app, if (u.contact.phone.value == null) R.color.md_grey_500 else u.contact.phone.color)
        twitterColor.value = ContextCompat.getColor(app, if (u.contact.twitter.value == null) R.color.md_grey_500 else u.contact.twitter.color)
        instagramColor.value = ContextCompat.getColor(app, if (u.contact.instagram.value == null) R.color.md_grey_500 else u.contact.instagram.color)
        websiteColor.value = ContextCompat.getColor(app, if (u.contact.website.value == null) R.color.md_grey_500 else u.contact.website.color)
    }
}
package lang

import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.xodus.traveli.R
import main.ApplicationClass

class LanguageEN(private val app: ApplicationClass) : LanguageInterface {
    override val fontLight: Typeface? get() = ResourcesCompat.getFont(app, R.font.font_en_light)
    override val fontMedium: Typeface? get() = ResourcesCompat.getFont(app, R.font.font_en_light)
    override val fontBold: Typeface? get() = ResourcesCompat.getFont(app, R.font.font_en_medium)
    override val locale = "EN"
    override val appName = "Template Five"
    override val openUrl = "Open URL"
    override val nothingToShow = "Nothing To Show!"
    override val checkConnectionAndTryAgain = "Please check your internet connection and try again."
    override val retry = "Retry"
    override val id = "ID"
    override val albumId = "Album ID"
    override val title = "Title"
    override val languageUpdated = "Language Updated"
    override val somethingWentWrong = "Something Went Wrong"
    override val pressBackAgainToExit = "Press back again to exit"
    override val loading = "Loading..."
    override val download = "Download"
    override val search: String = "Search"
    override val trending: String = "Trending"
    override val countries: String = "Countries"
    override val cities: String = "Cities"
    override val home: String = "Home"
    override val new: String = "New"
    override val travel: String = "Travel"
    override val user: String = "User"
    override val travelTo: String = "Travel to"
    override val tags: String = "Tags"

    //Profile
    override val confirm: String = "Confirm"
    override val cancel: String = "Cancel"
    override val noTravels: String = "No Travels"
    override val fromTraveli: String = "From Traveli"
    override val travels: String = "Travels"
    override val stats: String = "Stats"
    override val contact: String = "Contact"
    override val add: String = "Add"
    override val phoneNumber: String = "Phone Number"
    override val emailAddress: String = "Email Address"
    override val twitterUsername: String = "Twitter Username"
    override val instagramUsername: String = "Instagram Username"
    override val websiteUrl: String = "Website Url"
    override val crop: String = "Crop"
    override val pleaseLoginToDoThisAction: String = "Please login to do this action."
    override val fileIsInvalid: String = "File is invalid."

    //Profile Edit
    override val name: String = "Name"
    override val hometown: String = "Hometown"
    override val bio: String = "Bio"
    override val logout: String = "Logout"
    override val deleteAccount: String = "Delete account"
    override val logoutDesc: String = "Are you sure you want to logout?"
    override val deleteAccountDesc: String = "Are you sure you want to delete your account permanently?\nAll your data will be lost.\nThis action cannot be undone."
}

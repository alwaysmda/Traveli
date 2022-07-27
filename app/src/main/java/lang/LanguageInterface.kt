package lang

import android.graphics.Typeface

interface LanguageInterface {
    val fontLight: Typeface?
    val fontMedium: Typeface?
    val fontBold: Typeface?

    val locale: String
    val appName: String
    val openUrl: String
    val nothingToShow: String
    val checkConnectionAndTryAgain: String
    val retry: String
    val id: String
    val albumId: String
    val title: String
    val languageUpdated: String
    val somethingWentWrong: String
    val pressBackAgainToExit: String
    val loading: String
    val download: String


    val search: String
    val trending: String
    val countries: String
    val cities: String
    val new: String
    val travel: String
    val user: String
    val travelTo: String

    //navigation
    val home: String

    //Profile
    val confirm: String
    val cancel: String
    val noTravels: String
    val fromTraveli: String
    val travels: String
    val stats: String
    val contact: String
    val add: String
    val phoneNumber: String
    val emailAddress: String
    val twitterUsername: String
    val instagramUsername: String
    val websiteUrl: String
    val crop: String
    val pleaseLoginToDoThisAction: String
    val fileIsInvalid: String

    fun paramString(param: String) = "$appName $param"
}
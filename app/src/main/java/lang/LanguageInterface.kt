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
    val tags: String

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

    //Profile Edit
    val name: String
    val hometown: String
    val bio: String
    val logout: String
    val deleteAccount: String
    val logoutDesc: String
    val deleteAccountDesc: String

    //Transaction
    val balance: String
    val checkout: String
    val yourTransactionDetailWillBeHere: String
    val confirmCheckout: String
    val confirmCheckoutDesc1: String
    val confirmCheckoutDesc2: String
    val checkoutComplete: String
    val chargeAccount: String
    val customAmount: String
    val priceCannotBeLowerThanX1: String
    val priceCannotBeLowerThanX2: String
    val priceCannotBeHigherThanX1: String
    val priceCannotBeHigherThanX2: String

    fun priceCannotBeLowerThanX(value: String) = "$priceCannotBeLowerThanX1 $value $priceCannotBeLowerThanX2"
    fun priceCannotBeHigherThanX(value: String) = "$priceCannotBeHigherThanX1 $value $priceCannotBeHigherThanX2"
    fun confirmCheckoutDesc(value: String) = "$confirmCheckoutDesc1 $value $confirmCheckoutDesc2"
    fun paramString(param: String) = "$appName $param"
}
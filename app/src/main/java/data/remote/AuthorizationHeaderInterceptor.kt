package data.remote

import android.content.Context
import android.telephony.TelephonyManager
import androidx.core.content.pm.PackageInfoCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import lang.LanguageManager
import main.ApplicationClass
import okhttp3.Interceptor
import okhttp3.Response
import util.Constant
import util.PrefManager
import util.extension.getAndroidID
import util.extension.getPackageInfo
import javax.inject.Inject

class AuthorizationHeaderInterceptor @Inject constructor(
    @ApplicationContext private val appClass: ApplicationClass,
    private val languageManager: LanguageManager,
    private val prefManager: PrefManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().apply {
            prefManager.getStringPref(Constant.PREF_TOKEN)?.let {
                addHeader("Authorization", "Bearer $it")
            }
            addHeader("Device-Id", getAndroidID())
            addHeader(
                "User-Agent",
                "Traveli"
                        + " "
                        + getPackageInfo().versionName
                        + " (Android; "
                        + PackageInfoCompat.getLongVersionCode(getPackageInfo()) + "; "
                        + (appClass.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).networkCountryIso + "; "
                        + getAndroidID()
                        + "; "
                        + languageManager.currentLanguage.value + ")"
            )
        }.build()
        return chain.proceed(request)
    }
}
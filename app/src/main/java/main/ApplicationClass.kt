package main

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import domain.model.User
import lang.LanguageInterface
import lang.LanguageManager
import util.Constant
import util.PrefManager
import util.ThemeManager
import javax.inject.Inject

@HiltAndroidApp
class ApplicationClass : Application() {
    @Inject
    lateinit var themeManager: ThemeManager

    @Inject
    lateinit var prefManager: PrefManager

    @Inject
    lateinit var languageManager: LanguageManager
    val m: LanguageInterface
        get() = languageManager.main
    var user: User? = null

    companion object {
        @Volatile
        private lateinit var instance: ApplicationClass
        fun getInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        System.loadLibrary("native")
        instance = this
        languageManager.changeLang(Constant.Languages.EN)
        //        setupPicasso()
    }

    external fun getBaseApi(): String
    //    private fun setupPicasso() {
    //        val client = OkHttpClient.Builder()
    //            .hostnameVerifier { _, _ -> true }
    //            .connectTimeout(30, TimeUnit.SECONDS)
    //            .writeTimeout(30, TimeUnit.SECONDS)
    //            .readTimeout(30, TimeUnit.SECONDS)
    //            .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
    //            .build()
    //        val picasso = Picasso.Builder(this)
    //            .downloader(OkHttp3Downloader(client))
    //            .listener { picasso, uri, exception ->
    //                log("PICASSO EXCEPTION", uri, exception.localizedMessage, exception.printStackTrace())
    //            }
    //            .build()
    //        Picasso.setSingletonInstance(picasso)
    //    }
}
//type user
/*
add travel preview type
add view types to travel detail
* */
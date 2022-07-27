package di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.xodus.traveli.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.remote.*
import lang.LanguageManager
import main.ApplicationClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.Constant.CON_BASE_TEMPLATE_URL
import util.PrefManager
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideGsonBuilder(): Gson =
        GsonBuilder().create()

    @Singleton
    @Provides
    fun providePhotoHeaderInterceptor(appClass: ApplicationClass, languageManager: LanguageManager): PhotoHeaderInterceptor =
        PhotoHeaderInterceptor(appClass, languageManager)

    @Singleton
    @Provides
    fun provideAuthorizationHeaderInterceptor(appClass: ApplicationClass, languageManager: LanguageManager, prefManager: PrefManager): AuthorizationHeaderInterceptor =
        AuthorizationHeaderInterceptor(appClass, languageManager, prefManager)

    @Provides
    @Singleton
    fun provideTemplateClient(authorizationHeaderInterceptor: AuthorizationHeaderInterceptor): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authorizationHeaderInterceptor)
        //.addInterceptor(GsonConverterFactory.create(gson))
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }
        return okHttpClient
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, templateClient: OkHttpClient.Builder): Retrofit =
        Retrofit
            .Builder()
            .client(templateClient.build())
            .baseUrl(CON_BASE_TEMPLATE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideTemplateApi(retrofit: Retrofit): PhotoApi = retrofit.create(PhotoApi::class.java)

    @Provides
    @Singleton
    fun providesTraveliApi(retrofit: Retrofit): TravelApi = retrofit.create(TravelApi::class.java)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideMiscApi(retrofit: Retrofit): MiscApi = retrofit.create(MiscApi::class.java)

    @Provides
    @Singleton
    fun provideTransactionApi(retrofit: Retrofit): TransactionApi = retrofit.create(TransactionApi::class.java)
}

package di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.xodus.templatefive.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.remote.PhotoApi
import data.remote.PhotoHeaderInterceptor
import data.remote.TraveliApi
import data.remote.UserApi
import lang.LanguageManager
import main.ApplicationClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.Constant
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
    fun provideHeaderInterceptor(appClass: ApplicationClass, languageManager: LanguageManager): PhotoHeaderInterceptor =
        PhotoHeaderInterceptor(appClass, languageManager)

    @Provides
    @Singleton
    fun provideTemplateClient(photoHeaderInterceptor: PhotoHeaderInterceptor): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(photoHeaderInterceptor)
        //.addInterceptor(GsonConverterFactory.create(gson))
        if (BuildConfig.DEBUG) {
            okHttpClient.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        }
        return okHttpClient
    }

    @Provides
    @Singleton
    fun provideTemplateApi(gson: Gson, templateClient: OkHttpClient.Builder): PhotoApi =
        Retrofit
            .Builder()
            .client(templateClient.build())
            .baseUrl(Constant.CON_BASE_TEMPLATE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PhotoApi::class.java)

    @Provides
    @Singleton
    fun providesTraveliApi(gson: Gson, client: OkHttpClient.Builder): TraveliApi =
        Retrofit
            .Builder()
            .client(client.build())
            .baseUrl(Constant.CON_BASE_TEMPLATE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TraveliApi::class.java)

    @Provides
    @Singleton
    fun provideUserApi(gson: Gson, client: OkHttpClient.Builder): UserApi =
        Retrofit
            .Builder()
            .client(client.build())
            .baseUrl(Constant.CON_BASE_TEMPLATE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserApi::class.java)
}

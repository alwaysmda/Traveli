package di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.xodus.templatefive.BuildConfig
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
            .baseUrl(CON_BASE_TEMPLATE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PhotoApi::class.java)

    @Provides
    @Singleton
    fun providesTraveliApi(gson: Gson, client: OkHttpClient.Builder): TravelApi =
        Retrofit
            .Builder()
            .client(client.build())
            .baseUrl(CON_BASE_TEMPLATE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TravelApi::class.java)

    @Provides
    @Singleton
    fun provideUserApi(gson: Gson, client: OkHttpClient.Builder): UserApi =
        Retrofit
            .Builder()
            .client(client.build())
            .baseUrl(CON_BASE_TEMPLATE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideApi(gson: Gson, client: OkHttpClient.Builder): Api =
        Retrofit.Builder()
            .client(client.build())
            .baseUrl(CON_BASE_TEMPLATE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Api::class.java)

}

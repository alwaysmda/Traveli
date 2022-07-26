package di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.media3.exoplayer.ExoPlayer
import com.xodus.templatefive.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import domain.repository.PhotoRepository
import domain.repository.Repository
import domain.repository.TraveliRepository
import domain.repository.UserRepository
import domain.usecase.country.CountryUseCases
import domain.usecase.country.GetCountryListUseCase
import domain.usecase.photo.*
import domain.usecase.template.Template
import domain.usecase.template.TemplateUseCases
import domain.usecase.travel.*
import domain.usecase.user.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import lang.LanguageManager
import main.ApplicationClass
import util.PrefManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplicationClass(@ApplicationContext app: Context): ApplicationClass =
        app as ApplicationClass

    @Singleton
    @Provides
    fun providePrefManager(app: ApplicationClass): PrefManager =
        PrefManager(app)

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext app: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                app.preferencesDataStoreFile(BuildConfig.APPLICATION_ID)
            }
        )

    @Singleton
    @Provides
    fun provideLanguageManager(app: ApplicationClass, prefManager: PrefManager): LanguageManager =
        LanguageManager(app, prefManager)

    @Singleton
    @Provides
    fun provideExoPlayer(@ApplicationContext context: Context) = ExoPlayer.Builder(context).build()


    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun providePhotoUseCases(photoRepository: PhotoRepository): PhotoUseCases {
        return PhotoUseCases(
            DownloadUseCase(photoRepository),
            GetPhoto(photoRepository),
            GetPhotoList(photoRepository),
            GetClientList(photoRepository),
            GetRoomList(photoRepository),
            GetRoomUserList(photoRepository),
        )
    }

    @Singleton
    @Provides
    fun provideTemplateUseCases(photoRepository: PhotoRepository): TemplateUseCases {
        return TemplateUseCases(
            Template(photoRepository),
        )
    }


    @Singleton
    @Provides
    fun provideTravelUseCase(traveliRepository: TraveliRepository): TravelUseCases {
        return TravelUseCases(
            GetTrendingListUseCase(traveliRepository),
            GetBannerUseCase(traveliRepository),
            GetNewTravelListUseCase(traveliRepository),
            SearchTravelsUseCase(traveliRepository),
            GetTravelDetailUseCase(traveliRepository)
        )
    }

    @Singleton
    @Provides
    fun providesCountryUseCases(repository: Repository): CountryUseCases {
        return CountryUseCases(
            GetCountryListUseCase(repository)
        )
    }

    @Singleton
    @Provides
    fun providesUserUseCases(userRepository: UserRepository): UserUseCases {
        return UserUseCases(
            SearchUser(userRepository),
            GetUserStat(userRepository),
            GetMe(userRepository),
            GetUser(userRepository),
        )
    }
}

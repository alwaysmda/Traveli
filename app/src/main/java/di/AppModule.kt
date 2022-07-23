package di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import domain.repository.PhotoRepository
import domain.repository.TraveliRepository
import domain.repository.UserRepository
import domain.usecase.country.CountryUseCases
import domain.usecase.country.GetCountry
import domain.usecase.photo.*
import domain.usecase.template.Template
import domain.usecase.template.TemplateUseCases
import domain.usecase.travel.*
import domain.usecase.user.GetUser
import domain.usecase.user.GetUserStat
import domain.usecase.user.UserUseCases
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
            GetTrending(traveliRepository),
            GetBanner(traveliRepository),
            GetNewTravel(traveliRepository),
            GetTravel(traveliRepository),
            GetTravelDetail(traveliRepository)
        )
    }

    @Singleton
    @Provides
    fun providesCountryUseCases(traveliRepository: TraveliRepository): CountryUseCases {
        return CountryUseCases(
            GetCountry(traveliRepository)
        )
    }

    @Singleton
    @Provides
    fun providesUserUseCases(userRepository: UserRepository): UserUseCases {
        return UserUseCases(
            GetUser(userRepository),
            GetUserStat(userRepository),
        )
    }
}

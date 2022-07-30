package di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.xodus.traveli.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import domain.repository.*
import domain.usecase.country.CountryUseCases
import domain.usecase.country.GetCountryListUseCase
import domain.usecase.photo.*
import domain.usecase.template.Template
import domain.usecase.template.TemplateUseCases
import domain.usecase.transaction.ChargeUseCase
import domain.usecase.transaction.CheckoutUseCase
import domain.usecase.transaction.GetTransactionListUseCase
import domain.usecase.transaction.TransactionUseCases
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
    fun provideExoPlayer(@ApplicationContext context: Context) = ExoPlayer.Builder(context).build().apply {
        setMediaItem(MediaItem.fromUri("https://persian5.cdn.asset.aparat.com/aparat-video/bdaea06a8c07f580a463d150bb9730af44790032-360p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImM1MDA0ZDM1ZDU4MGJjMzk5NDVmNjk2Y2IwMTQ5NmM2IiwiZXhwIjoxNjU4OTIwOTQ4LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.kPJww86JRZqRSrjri07ip61IWYQwwZO217zJ-kfaXks"))
    }


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
    fun providesCountryUseCases(miscRepository: MiscRepository): CountryUseCases {
        return CountryUseCases(
            GetCountryListUseCase(miscRepository)
        )
    }

    @Singleton
    @Provides
    fun provideUserUseCases(app: ApplicationClass, userRepository: UserRepository, prefManager: PrefManager): UserUseCases {
        return UserUseCases(
            SearchUserUseCase(userRepository),
            GetUserStatUseCase(userRepository),
            GetMeUseCase(app, userRepository, prefManager),
            GetUserUseCase(userRepository),
            GetUserTravelListUseCase(userRepository),
            UpdateUserInfoUseCase(app, userRepository, prefManager),
            UpdateCoverUseCase(app, userRepository, prefManager),
            UpdateAvatarUseCase(app, userRepository, prefManager),
            UpdateContactUseCase(app, userRepository, prefManager),
            LogoutUseCase(app, prefManager),
            DeleteAccountUseCase(app, userRepository, prefManager),
        )
    }

    @Singleton
    @Provides
    fun provideTransactionUseCases(app: ApplicationClass, transactionRepository: TransactionRepository, prefManager: PrefManager): TransactionUseCases {
        return TransactionUseCases(
            GetTransactionListUseCase(app, transactionRepository, prefManager),
            ChargeUseCase(app, transactionRepository, prefManager),
            CheckoutUseCase(app, transactionRepository, prefManager),
        )
    }
}

package di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.remote.MiscApi
import data.remote.PhotoApi
import data.remote.TravelApi
import data.remote.UserApi
import data.remote.dto.*
import data.remote.dto.country.CountryMapper
import data.remote.dto.travel.TravelMapper
import data.remote.dto.travelDetail.TravelDetailMapper
import data.repository.PhotoRepositoryImpl
import data.repository.RepositoryImpl
import data.repository.TravelRepositoryImpl
import data.repository.UserRepositoryImpl
import domain.repository.PhotoRepository
import domain.repository.Repository
import domain.repository.TraveliRepository
import domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import main.ApplicationClass
import util.PrefManager
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTemplateRepository(app: ApplicationClass, api: PhotoApi, photoMapper: PhotoMapper, networkErrorMapper: NetworkErrorMapper): PhotoRepository =
        PhotoRepositoryImpl(app, api, networkErrorMapper, photoMapper)

    @Provides
    @Singleton
    fun providesTraveliRepository(
        app: ApplicationClass,
        api: TravelApi,
        networkErrorMapper: NetworkErrorMapper,
    ): TraveliRepository = TravelRepositoryImpl(
        api,
        app,
        networkErrorMapper,
        TravelDetailMapper(),
        TravelMapper()
    )

    @Provides
    @Singleton
    fun providesUserRepository(
        app: ApplicationClass,
        prefManager: PrefManager,
        api: UserApi,
        networkErrorMapper: NetworkErrorMapper,
        userPreviewMapper: UserPreviewMapper,
        userMapper: UserMapper,
        statMapper: StatMapper,
    ): UserRepository = UserRepositoryImpl(
        api,
        app,
        prefManager,
        networkErrorMapper,
        userPreviewMapper,
        userMapper,
        statMapper,
    )

    @Provides
    @Singleton
    fun provideRepository(
        app: ApplicationClass,
        miscApi: MiscApi,
        networkErrorMapper: NetworkErrorMapper,
        countryMapper: CountryMapper
    ): Repository = RepositoryImpl(miscApi, app, networkErrorMapper, countryMapper)
}
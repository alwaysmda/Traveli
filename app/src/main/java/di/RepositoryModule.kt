package di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.remote.PhotoApi
import data.remote.TraveliApi
import data.remote.UserApi
import data.remote.dto.NetworkErrorMapper
import data.remote.dto.PhotoMapper
import data.remote.dto.StatMapper
import data.remote.dto.travelDetail.TravelDetailMapper
import data.repository.PhotoRepositoryImpl
import data.repository.TravelRepositoryImpl
import data.repository.UserRepositoryImpl
import domain.repository.PhotoRepository
import domain.repository.TraveliRepository
import domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import main.ApplicationClass
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
        api: TraveliApi,
        networkErrorMapper: NetworkErrorMapper,
    ): TraveliRepository = TravelRepositoryImpl(
        api,
        app,
        networkErrorMapper,
        TravelDetailMapper()
    )

    @Provides
    @Singleton
    fun providesUserRepository(
        app: ApplicationClass,
        api: UserApi,
        networkErrorMapper: NetworkErrorMapper,
        statMapper: StatMapper,
    ): UserRepository = UserRepositoryImpl(
        api,
        app,
        networkErrorMapper,
        statMapper,
    )
}
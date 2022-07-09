package di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.remote.PhotoApi
import data.remote.dto.NetworkErrorMapper
import data.remote.dto.PhotoMapper
import data.remote.dto.TraveliApi
import data.repository.PhotoRepositoryImpl
import data.repository.TravelRepositoryImpl
import domain.repository.PhotoRepository
import domain.repository.TraveliRepository
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
    fun providesTraveliRepository(app:ApplicationClass,api:TraveliApi,networkErrorMapper: NetworkErrorMapper):TraveliRepository = TravelRepositoryImpl(api,app,networkErrorMapper)

}
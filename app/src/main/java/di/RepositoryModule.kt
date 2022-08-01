package di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.remote.*
import data.remote.dto.*
import data.repository.*
import domain.repository.*
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
        travelMapper: TravelMapper,
        travelPreviewMapper: TravelPreviewMapper
    ): TraveliRepository = TravelRepositoryImpl(
        api,
        app,
        networkErrorMapper,
        travelPreviewMapper,
        travelMapper
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
        travelPreviewMapper: TravelPreviewMapper,
    ): UserRepository = UserRepositoryImpl(
        api,
        app,
        prefManager,
        networkErrorMapper,
        userPreviewMapper,
        userMapper,
        statMapper,
        travelPreviewMapper,
    )

    @Provides
    @Singleton
    fun providesTransactionRepository(
        app: ApplicationClass,
        networkErrorMapper: NetworkErrorMapper,
        transactionApi: TransactionApi,
        balanceMapper: BalanceMapper,
        transactionDataMapper: TransactionDataMapper,
    ): TransactionRepository = TransactionRepositoryImpl(
        app,
        networkErrorMapper,
        transactionApi,
        balanceMapper,
        transactionDataMapper,
    )

    @Provides
    @Singleton
    fun provideMiscRepository(
        app: ApplicationClass,
        miscApi: MiscApi,
        networkErrorMapper: NetworkErrorMapper,
        countryMapper: CountryMapper
    ): MiscRepository = MiscRepositoryImpl(miscApi, app, networkErrorMapper, countryMapper)
}
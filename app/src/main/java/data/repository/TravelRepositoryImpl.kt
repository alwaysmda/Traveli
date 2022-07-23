package data.repository

import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.TraveliApi
import data.remote.dto.NetworkErrorMapper
import domain.model.Country
import domain.model.travel.Banner
import domain.model.travel.Travel
import domain.model.travel.TravelDetail
import domain.repository.TraveliRepository
import main.ApplicationClass

class TravelRepositoryImpl(
    private val traveliApi: TraveliApi,
    private val app: ApplicationClass,
    private val networkErrorMapper: NetworkErrorMapper,
) : TraveliRepository, ApiResponseHandler(app, networkErrorMapper) {
    private var trending: List<Travel>? = null
    private var newTravel: List<Travel>? = null
    private var banner: Banner? = null
    private var countries: List<Country>? = null

    override suspend fun getTrending(): DataState<List<Travel>> {
        trending?.let { return DataState.Success(it) }
        return when (val response = call { traveliApi.getTrending() }) {
            is DataState.Failure -> response
            is DataState.Loading -> DataState.Loading
            is DataState.Success -> {
                trending = Travel.getFake(10)
                DataState.Success(Travel.getFake(10))
            }
        }
    }

    override suspend fun getNewTravels(): DataState<List<Travel>> {
        newTravel?.let { return DataState.Success(it) }
        return when (val response = call { traveliApi.getNewTravel() }) {
            is DataState.Failure -> response
            is DataState.Loading -> response
            is DataState.Success -> {
                newTravel = Travel.getFake(10)
                DataState.Success(Travel.getFake(10))
            }
        }
    }

    override suspend fun getTravel(): DataState<List<Travel>> {
        return when (val response = call { traveliApi.getTravel() }) {
            is DataState.Failure -> response
            is DataState.Loading -> response
            is DataState.Success -> DataState.Success(Travel.getFake(5))
        }
    }

    override suspend fun getTravelDetail(travelId: Int): DataState<List<TravelDetail>> {
        return when (val response = call { traveliApi.getTravelDetail() }) {
            is DataState.Failure -> response
            DataState.Loading    -> DataState.Loading
            is DataState.Success -> DataState.Success(TravelDetail.getFakes())
        }
    }

    override suspend fun getBanner(): DataState<Banner> {
        banner?.let { return DataState.Success(it) }
        return when (val response = call { traveliApi.getBanner() }) {
            is DataState.Failure -> response
            DataState.Loading    -> DataState.Loading
            is DataState.Success -> {
                banner = Banner.getFake()
                DataState.Success(Banner.getFake())
            }
        }
    }

    override suspend fun getCountries(): DataState<List<Country>> {
        countries?.let { return DataState.Success(it) }
        return when (val response = call { traveliApi.getCountries() }) {
            is DataState.Failure -> response
            DataState.Loading    -> DataState.Loading
            is DataState.Success -> {
                countries = Country.getFakes()
                DataState.Success(Country.getFakes())
            }
        }
    }
}
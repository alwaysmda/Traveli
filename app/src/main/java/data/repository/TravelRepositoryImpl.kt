package data.repository


import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.TravelApi
import data.remote.dto.NetworkErrorMapper
import data.remote.dto.travel.ResponseTravelDto
import data.remote.dto.travel.TravelMapper
import data.remote.dto.travelDetail.ResponseTravelDetailDto
import data.remote.dto.travelDetail.TravelDetailMapper
import domain.model.Country
import domain.model.travel.Banner
import domain.model.travel.Travel
import domain.model.travel.TravelDetail
import domain.repository.TraveliRepository
import main.ApplicationClass

class TravelRepositoryImpl(
    private val travelApi: TravelApi,
    private val app: ApplicationClass,
    private val networkErrorMapper: NetworkErrorMapper,
    private val travelDetailMapper: TravelDetailMapper,
    private val travelMapper: TravelMapper,

    ) : TraveliRepository, ApiResponseHandler(app, networkErrorMapper) {
    private var trending: List<Travel>? = null
    private var newTravel: List<Travel>? = null
    private var banner: Banner? = null
    private var countries: List<Country>? = null

    override suspend fun getTrending(): DataState<List<Travel>> {
        trending?.let { return DataState.Success(it) }
        return when (val response = call { travelApi.getTrending() }) {
            is DataState.Failure -> response
            is DataState.Loading -> {
                trending = ResponseTravelDto.getFake().travels.map { travelMapper.toDomainModel(it) }
                DataState.Success(trending!!)
            }
            is DataState.Success -> DataState.Loading
        }
    }

    override suspend fun getNewTravels(): DataState<List<Travel>> {
        newTravel?.let { return DataState.Success(it) }
        return when (val response = call { travelApi.getNewTravel() }) {
            is DataState.Failure -> response
            is DataState.Loading -> {
                newTravel = ResponseTravelDto.getFake().travels.map { travelMapper.toDomainModel(it) }
                DataState.Success(newTravel!!)
            }
            is DataState.Success -> DataState.Loading
        }
    }

    override suspend fun getTravel(): DataState<List<Travel>> {
        return when (val response = call { travelApi.getTravel("") }) {
            is DataState.Failure -> response
            is DataState.Loading -> {
                DataState.Success(ResponseTravelDto.getFake().travels.map { travelMapper.toDomainModel(it) })
            }
            is DataState.Success -> DataState.Loading
        }
    }

    override suspend fun getTravelDetail(travelId: Int): DataState<List<TravelDetail>> {
        return when (val response = call { travelApi.getTravelDetail(travelId) }) {
            is DataState.Failure -> response
            DataState.Loading    -> DataState.Success(ResponseTravelDetailDto.getFake().travelDetailDto.map { travelDetailMapper.toDomainModel(it) })
            is DataState.Success -> DataState.Loading
        }
    }

    override suspend fun getBanner(): DataState<Banner> {
        banner?.let { return DataState.Success(it) }
        return when (val response = call { travelApi.getBanner() }) {
            is DataState.Failure -> response
            DataState.Loading    -> {
                banner = Banner.getFake()
                DataState.Success(Banner.getFake())
            }
            is DataState.Success -> DataState.Loading
        }
    }


}
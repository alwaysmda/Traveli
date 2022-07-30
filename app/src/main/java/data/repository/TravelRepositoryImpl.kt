package data.repository


import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.TravelApi
import data.remote.dto.*
import domain.model.Banner
import domain.model.Travel
import domain.model.TravelPreview
import domain.repository.TraveliRepository
import main.ApplicationClass

class TravelRepositoryImpl(
    private val travelApi: TravelApi,
    private val app: ApplicationClass,
    private val networkErrorMapper: NetworkErrorMapper,
    private val travelPreviewMapper: TravelPreviewMapper,
    private val travelMapper: TravelMapper

) : TraveliRepository, ApiResponseHandler(app, networkErrorMapper) {
    private val trending = arrayListOf<TravelPreview>()
    private var newTravelPreview: List<TravelPreview>? = null
    private var banner: Banner? = null

    override suspend fun getTrending(page: Int): DataState<List<TravelPreview>> {
        return if (trending.isEmpty()) {
            return when (val response = call { travelApi.getTrending() }) {
                is DataState.Failure -> response
                is DataState.Loading -> {
                    trending.addAll(ResponseTravelListDto.getFake().travels.map { travelPreviewMapper.toDomainModel(it) })
                    DataState.Success(trending)
                }
                is DataState.Success -> DataState.Loading
            }
        } else {
            DataState.Success(trending)
        }
    }

    override suspend fun getNewTravels(page: Int): DataState<List<TravelPreview>> {
        newTravelPreview?.let { return DataState.Success(it) }
        return when (val response = call { travelApi.getNewTravels(page) }) {
            is DataState.Failure -> response
            is DataState.Loading -> {
                newTravelPreview = ResponseTravelListDto.getFake().travels.map { travelPreviewMapper.toDomainModel(it) }
                DataState.Success(newTravelPreview!!)
            }
            is DataState.Success -> DataState.Loading
        }
    }

    override suspend fun searchTravel(query: String, page: Int): DataState<List<TravelPreview>> {
        return when (val response = call { travelApi.getTravels("", page) }) {
            is DataState.Failure -> response
            is DataState.Loading -> {
                DataState.Success(ResponseTravelListDto.getFake().travels.map { travelPreviewMapper.toDomainModel(it) })
            }
            is DataState.Success -> DataState.Loading
        }
    }

    override suspend fun getTravelDetail(travelId: Int): DataState<Travel> {
        return when (val response = call { travelApi.getTravelDetail(travelId) }) {
            is DataState.Failure -> response
            DataState.Loading    -> DataState.Success(travelMapper.toDomainModel(ResponseTravelDetailDto.getFake().travel))
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
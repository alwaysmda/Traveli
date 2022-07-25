package data.repository


import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.TravelApi
import data.remote.dto.*
import domain.model.Country
import domain.model.travel.Banner
import domain.model.travel.TravelDetail
import domain.model.travel.TravelPreview
import domain.repository.TraveliRepository
import main.ApplicationClass

class TravelRepositoryImpl(
    private val travelApi: TravelApi,
    private val app: ApplicationClass,
    private val networkErrorMapper: NetworkErrorMapper,
    private val travelDetailMapper: TravelDetailMapper,
    private val travelPreviewMapper: TravelPreviewMapper,

    ) : TraveliRepository, ApiResponseHandler(app, networkErrorMapper) {
    private var trending: List<TravelPreview>? = null
    private var newTravelPreview: List<TravelPreview>? = null
    private var banner: Banner? = null
    private var countries: List<Country>? = null

    override suspend fun getTrending(): DataState<List<TravelPreview>> {
        trending?.let { return DataState.Success(it) }
        return when (val response = call { travelApi.getTrending() }) {
            is DataState.Failure -> response
            is DataState.Loading -> {
                trending = ResponseTravelDto.getFake().travels.map { travelPreviewMapper.toDomainModel(it) }
                DataState.Success(trending!!)
            }
            is DataState.Success -> DataState.Loading
        }
    }

    override suspend fun getNewTravels(): DataState<List<TravelPreview>> {
        newTravelPreview?.let { return DataState.Success(it) }
        return when (val response = call { travelApi.getNewTravel() }) {
            is DataState.Failure -> response
            is DataState.Loading -> {
                newTravelPreview = ResponseTravelDto.getFake().travels.map { travelPreviewMapper.toDomainModel(it) }
                DataState.Success(newTravelPreview!!)
            }
            is DataState.Success -> DataState.Loading
        }
    }

    override suspend fun getTravel(): DataState<List<TravelPreview>> {
        return when (val response = call { travelApi.getTravel("") }) {
            is DataState.Failure -> response
            is DataState.Loading -> {
                DataState.Success(ResponseTravelDto.getFake().travels.map { travelPreviewMapper.toDomainModel(it) })
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
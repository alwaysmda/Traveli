package data.repository

import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.dto.NetworkErrorMapper
import data.remote.dto.TraveliApi
import domain.model.travel.Travel
import domain.repository.TraveliRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import main.ApplicationClass


const val carabianSeeImage = "https://www.wildearth-travel.com/get-image-version/verybig/uploads/caribbean_sea_11_days_(fram)_picture.jpg"
const val rioImage = "https://upload-gifs.s3-sa-east-1.amazonaws.com/ae79d48c-5994-449a-a10a-f02a0ea455b0_Cristo+Redentor+Riotur.jpg"

class TravelRepositoryImpl(
    private val traveliApi: TraveliApi,
    private val app: ApplicationClass,
    private val networkErrorMapper: NetworkErrorMapper

) : TraveliRepository, ApiResponseHandler(app, networkErrorMapper) {


    override  fun getTravel(isTrending: Boolean, isNew: Boolean) = flow<DataState<List<Travel>>> {
        when (val response = call(traveliApi.getTravel())) {
            is DataState.Failure -> emit(response)
            DataState.Loading    -> emit(DataState.Loading)
            is DataState.Success -> {
                val travels = mutableListOf<Travel>()
                for (x in 0..10) {
                    travels.add(Travel(x.toString(), "caribbean sea", carabianSeeImage))
                }
                emit(DataState.Success(travels))
            }

        }


    }

}
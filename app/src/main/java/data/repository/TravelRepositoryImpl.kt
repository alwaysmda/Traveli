package data.repository

import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.dto.NetworkErrorMapper
import data.remote.dto.TraveliApi
import domain.model.Travel
import domain.repository.TraveliRepository
import main.ApplicationClass


const val carabianSeeImage =  "https://www.wildearth-travel.com/get-image-version/verybig/uploads/caribbean_sea_11_days_(fram)_picture.jpg"

class TravelRepositoryImpl(
    private val traveliApi: TraveliApi,
    private val app: ApplicationClass,
    private val networkErrorMapper: NetworkErrorMapper

    ) : TraveliRepository, ApiResponseHandler(app,networkErrorMapper) {
    override suspend fun getTravel(isTrending: Boolean, isNew: Boolean): DataState<List<Travel>> {
        return when (val response = call(traveliApi.getTravel(isTrending,isNew))){
            is DataState.Failure -> {
                DataState.Failure(response.code,"")
            }
            DataState.Loading    -> DataState.Loading
            is DataState.Success -> {
                val travels = mutableListOf<Travel>()
                for (x in 0..10){
                    travels.add(Travel(x.toString(),"caribbean sea", carabianSeeImage))
                }
                DataState.Success(travels)
            }
        }


    }
}
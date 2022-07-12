package data.repository

import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.dto.NetworkErrorMapper
import data.remote.dto.TraveliApi
import domain.model.Country
import domain.model.travel.Banner
import domain.model.travel.Travel
import domain.repository.TraveliRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import main.ApplicationClass


class TravelRepositoryImpl(
    private val traveliApi: TraveliApi,
    private val app: ApplicationClass,
    private val networkErrorMapper: NetworkErrorMapper

) : TraveliRepository, ApiResponseHandler(app, networkErrorMapper) {


    override fun getTravel(isTrending: Boolean, isNew: Boolean) = flow {

        when (val response = call { traveliApi.getTravel() }) {
            is DataState.Loading -> emit(response)
            is DataState.Failure -> emit(response)
            is DataState.Success -> {
                emit(DataState.Success(Travel.getFake(10)))
            }
        }
    }


    override fun getBanner() = flow {
        when (val response = call { traveliApi.getBanner() }) {
            is DataState.Failure -> emit(response)
            DataState.Loading    -> emit(DataState.Loading)
            is DataState.Success -> emit(DataState.Success(Banner.getFake()))
        }

    }

    override fun getCountries() = flow<DataState<List<Country>>> {
        when(val response = call { traveliApi.getCountries() }){
            is DataState.Failure -> emit(response)
            DataState.Loading    -> emit(DataState.Loading)
            is DataState.Success -> emit(DataState.Success(Country.getFakes()))
        }
    }


}
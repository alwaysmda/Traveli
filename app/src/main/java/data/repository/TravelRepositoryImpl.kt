package data.repository

import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.dto.NetworkErrorMapper
import data.remote.dto.TraveliApi
import domain.model.Country
import domain.model.travel.Banner
import domain.model.travel.Travel
import domain.repository.TraveliRepository
import main.ApplicationClass


class TravelRepositoryImpl(
    private val traveliApi: TraveliApi,
    private val app: ApplicationClass,
    private val networkErrorMapper: NetworkErrorMapper

) : TraveliRepository, ApiResponseHandler(app, networkErrorMapper) {


    override suspend fun getTrending():DataState<List<Travel>> {
       return when (val response = call { traveliApi.getTrending() }) {
            is DataState.Failure -> response
            is DataState.Loading -> DataState.Loading
            is DataState.Success -> {
                DataState.Success(Travel.getFake(10))
            }
        }
    }

    override suspend fun getNewTravels(): DataState<List<Travel>> {
        return when(val response = call { traveliApi.getNewTravel() }){
            is DataState.Failure -> response
            is DataState.Loading    -> response
            is DataState.Success -> DataState.Success(Travel.getFake(10))
        }
    }


    override suspend fun getBanner():DataState<Banner> {
      return  when (val response = call { traveliApi.getBanner() }) {
            is DataState.Failure -> response
            DataState.Loading    -> DataState.Loading
            is DataState.Success -> DataState.Success(Banner.getFake())
        }

    }

    override suspend fun getCountries() : DataState<List<Country>> {
       return when(val response = call { traveliApi.getCountries() }){
            is DataState.Failure -> response
            DataState.Loading    -> DataState.Loading
            is DataState.Success -> DataState.Success(Country.getFakes())
        }
    }


}
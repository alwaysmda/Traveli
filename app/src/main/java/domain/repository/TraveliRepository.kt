package domain.repository

import data.remote.DataState
import domain.model.Country
import domain.model.travel.Banner
import domain.model.travel.Travel
import domain.model.travel.TravelDetail

interface TraveliRepository {

    suspend fun getTrending(): DataState<List<Travel>>

    suspend fun getNewTravels(): DataState<List<Travel>>

    suspend fun getTravel(): DataState<List<Travel>>

    suspend fun getTravelDetail(travelId: Int): DataState<List<TravelDetail>>

    suspend fun getBanner(): DataState<Banner>

    suspend fun getCountries(): DataState<List<Country>>

}
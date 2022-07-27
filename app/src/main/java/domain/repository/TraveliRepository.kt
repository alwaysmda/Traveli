package domain.repository

import data.remote.DataState
import domain.model.Banner
import domain.model.Travel
import domain.model.TravelPreview

interface TraveliRepository {

    suspend fun getTrending(): DataState<List<TravelPreview>>

    suspend fun getNewTravels(): DataState<List<TravelPreview>>

    suspend fun searchTravel(query: String): DataState<List<TravelPreview>>

    suspend fun getTravelDetail(travelId: Int): DataState<Travel>

    suspend fun getBanner(): DataState<Banner>

}
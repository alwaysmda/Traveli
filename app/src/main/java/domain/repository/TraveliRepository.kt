package domain.repository

import data.remote.DataState
import domain.model.Banner
import domain.model.Travel
import domain.model.TravelPreview

interface TraveliRepository {

    suspend fun getTrending(page: Int): DataState<List<TravelPreview>>

    suspend fun getNewTravels(page: Int): DataState<List<TravelPreview>>

    suspend fun searchTravel(query: String, page: Int): DataState<List<TravelPreview>>

    suspend fun getTravelDetail(travelId: Long): DataState<Travel>

    suspend fun getBanner(): DataState<Banner>

}
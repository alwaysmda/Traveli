package domain.repository

import data.remote.DataState
import domain.model.travel.Banner
import domain.model.travel.TravelDetail
import domain.model.travel.TravelPreview

interface TraveliRepository {

    suspend fun getTrending(): DataState<List<TravelPreview>>

    suspend fun getNewTravels(): DataState<List<TravelPreview>>

    suspend fun getTravel(): DataState<List<TravelPreview>>

    suspend fun getTravelDetail(travelId: Int): DataState<List<TravelDetail>>

    suspend fun getBanner(): DataState<Banner>


}
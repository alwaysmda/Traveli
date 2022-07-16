package domain.repository

import data.remote.DataState
import domain.model.Country
import domain.model.User
import domain.model.travel.Banner
import domain.model.travel.Travel
import kotlinx.coroutines.flow.Flow

interface TraveliRepository {

    suspend fun getTrending(): DataState<List<Travel>>

    suspend fun getNewTravels(): DataState<List<Travel>>

    suspend fun getTravel(): DataState<List<Travel>>

    suspend fun getBanner(): DataState<Banner>

    suspend fun getCountries(): DataState<List<Country>>

    suspend fun getUsers(query: String): DataState<List<User>>


}
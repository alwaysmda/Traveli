package domain.repository

import data.remote.DataState
import domain.model.Travel

interface TraveliRepository {

    suspend fun getTravel(isTrending:Boolean,isNew:Boolean):DataState<List<Travel>>

}
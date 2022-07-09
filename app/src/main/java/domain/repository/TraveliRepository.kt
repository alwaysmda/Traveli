package domain.repository

import data.remote.DataState
import domain.model.travel.Travel
import kotlinx.coroutines.flow.Flow

interface TraveliRepository {

     fun getTravel(isTrending:Boolean,isNew:Boolean):Flow<DataState<List<Travel>>>

}
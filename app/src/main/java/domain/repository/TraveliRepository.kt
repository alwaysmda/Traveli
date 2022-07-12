package domain.repository

import data.remote.DataState
import domain.model.Country
import domain.model.travel.Banner
import domain.model.travel.Travel
import kotlinx.coroutines.flow.Flow

interface TraveliRepository {

     fun getTravel(isTrending:Boolean,isNew:Boolean):Flow<DataState<List<Travel>>>

     fun getBanner():Flow<DataState<Banner>>

     fun getCountries():Flow<DataState<List<Country>>>



}
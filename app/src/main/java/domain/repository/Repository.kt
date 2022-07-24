package domain.repository

import data.remote.DataState
import domain.model.Country

interface Repository {

    suspend fun getCountries(): DataState<ArrayList<Country>>


}
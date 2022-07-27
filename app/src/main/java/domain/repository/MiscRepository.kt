package domain.repository

import data.remote.DataState
import domain.model.Country

interface MiscRepository {
    suspend fun getCountries(): DataState<ArrayList<Country>>
}
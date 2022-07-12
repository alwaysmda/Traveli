package domain.usecase.country

import data.remote.DataState
import domain.repository.TraveliRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCountry(private val repo:TraveliRepository) {
    operator fun invoke() = flow{
        emit(DataState.Loading)
        val result = repo.getCountries()
        emit(result)
    }.flowOn(Dispatchers.IO)
}
package domain.usecase.travel

import data.remote.DataState
import domain.repository.TraveliRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetNewTravelListUseCase(private val repo: TraveliRepository) {
    operator fun invoke(page: Int = 1) = flow {
        emit(DataState.Loading)
        emit(repo.getNewTravels(page))
    }.flowOn(Dispatchers.IO)
}
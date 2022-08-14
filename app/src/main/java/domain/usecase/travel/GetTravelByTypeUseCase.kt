package domain.usecase.travel

import data.remote.DataState
import domain.repository.TraveliRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTravelByTypeUseCase(private val repo: TraveliRepository) {

    operator fun invoke(type: String, page: Int) = flow {
        emit(DataState.Loading)
        emit(repo.getTravelByType(type, page))
    }.flowOn(Dispatchers.IO)
}
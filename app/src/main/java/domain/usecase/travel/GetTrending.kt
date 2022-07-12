package domain.usecase.travel

import data.remote.DataState
import domain.repository.TraveliRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTrending(private val traveliRepo: TraveliRepository) {

    operator fun invoke() = flow {
        emit(DataState.Loading)
        emit(traveliRepo.getTrending())
    }.flowOn(Dispatchers.IO)
}
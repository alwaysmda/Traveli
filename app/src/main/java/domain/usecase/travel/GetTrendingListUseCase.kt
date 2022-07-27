package domain.usecase.travel

import data.remote.DataState
import domain.repository.TraveliRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTrendingListUseCase(private val traveliRepo: TraveliRepository) {

    operator fun invoke(page: Int = 1) = flow {
        emit(DataState.Loading)
        emit(traveliRepo.getTrending(page))
    }.flowOn(Dispatchers.IO)
}
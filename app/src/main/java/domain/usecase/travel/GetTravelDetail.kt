package domain.usecase.travel

import data.remote.DataState
import domain.repository.TraveliRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTravelDetail(private val traveliRepo: TraveliRepository) {


    operator fun invoke(travelId: Int) = flow {
        emit(DataState.Loading)
        emit(traveliRepo.getTravelDetail(travelId))
    }.flowOn(Dispatchers.IO)


}
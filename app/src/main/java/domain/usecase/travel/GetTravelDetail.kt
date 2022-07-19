package domain.usecase.travel

import data.remote.DataState
import domain.repository.TraveliRepository
import kotlinx.coroutines.flow.flow

class GetTravelDetail(private val traveliRepo: TraveliRepository) {


    operator fun invoke(travelId: Int) = flow {
        emit(DataState.Loading)
        emit(traveliRepo.getTravelDetail(travelId))
    }


}
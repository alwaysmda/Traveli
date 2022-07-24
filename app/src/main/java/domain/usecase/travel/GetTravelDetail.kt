package domain.usecase.travel

import data.remote.DataState
import domain.model.travel.TravelDetail
import domain.repository.TraveliRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTravelDetail(private val traveliRepo: TraveliRepository) {


    operator fun invoke(travelId: Int) = flow {
        emit(DataState.Loading)
        when (val result = traveliRepo.getTravelDetail(travelId)) {
            is DataState.Failure -> emit(result)
            DataState.Loading    -> emit(result)
            is DataState.Success -> {
                val travelDetails = result.data.toMutableList()
                travelDetails.add(TravelDetail.BookMark)
                emit(DataState.Success(travelDetails))
            }
        }
    }.flowOn(Dispatchers.IO)


}
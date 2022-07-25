package domain.usecase.travel

import data.remote.DataState
import domain.model.Travel
import domain.model.TravelDetail
import domain.model.TravelDetail.Companion.VIEW_TYPE_BOOKMARK
import domain.repository.TraveliRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTravelDetail(private val traveliRepo: TraveliRepository) {


    operator fun invoke(travelId: Int) = flow<DataState<Travel>> {
        emit(DataState.Loading)
        when (val result = traveliRepo.getTravelDetail(travelId)) {
            is DataState.Failure -> emit(result)
            DataState.Loading    -> emit(result)
            is DataState.Success -> {
                val viewTypes = result.data.details
                viewTypes.add(VIEW_TYPE_BOOKMARK, TravelDetail.BookMark)

                emit(DataState.Success(result.data))
            }
        }
    }.flowOn(Dispatchers.IO)


}
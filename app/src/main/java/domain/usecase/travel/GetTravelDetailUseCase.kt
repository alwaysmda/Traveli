package domain.usecase.travel

import data.remote.DataState
import domain.model.TravelDetail
import domain.repository.TraveliRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTravelDetailUseCase(private val traveliRepo: TraveliRepository) {


    operator fun invoke(travelId: Int) = flow<DataState<List<TravelDetail>>> {
        emit(DataState.Loading)
        when (val result = traveliRepo.getTravelDetail(travelId)) {
            is DataState.Failure -> emit(DataState.Failure(result.code, result.message))
            DataState.Loading    -> emit(DataState.Loading)
            is DataState.Success -> {
                val details = result.data.details
                details.add(TravelDetail.BookMark(result.data.isBookmarked))
                details.add(2, TravelDetail.User(result.data.user))
                emit(DataState.Success(details))
            }
        }
    }.flowOn(Dispatchers.IO)


}
package domain.usecase.travel

import data.remote.DataState
import domain.model.TravelDetail
import domain.repository.TraveliRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetTravelDetailUseCase(private val traveliRepo: TraveliRepository) {


    operator fun invoke(travelId: Long) = flow<DataState<List<TravelDetail>>> {
        emit(DataState.Loading)
        when (val result = traveliRepo.getTravelDetail(travelId)) {
            is DataState.Failure -> emit(DataState.Failure(result.code, result.message))
            DataState.Loading    -> emit(DataState.Loading)
            is DataState.Success -> {
                val details = result.data.details
                details.add(0, TravelDetail.User(result.data.user))
                details.add(0, TravelDetail.Cover(result.data.name, result.data.image))
                details.add(TravelDetail.City(result.data.cityList))
                details.add(TravelDetail.Tag(result.data.tagList))
                details.add(TravelDetail.BookMark(result.data.isBookmarked))

                emit(DataState.Success(details))
            }
        }
    }.flowOn(Dispatchers.IO)


}
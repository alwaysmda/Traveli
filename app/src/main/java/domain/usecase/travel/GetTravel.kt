package domain.usecase.travel

import data.remote.DataState
import domain.repository.TraveliRepository
import kotlinx.coroutines.flow.flow

class GetTravel(private val traveliRepo: TraveliRepository) {

    operator fun invoke(isTrending: Boolean = true, isNew: Boolean = true) = flow {
        when (val result = traveliRepo.getTravel(isTrending, isNew)) {
            is DataState.Failure -> emit(result)
            DataState.Loading    -> emit(DataState.Loading)
            is DataState.Success -> emit(DataState.Success(result.data))
        }

    }
}
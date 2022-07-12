package domain.usecase.travel

import data.remote.DataState
import domain.repository.TraveliRepository
import kotlinx.coroutines.flow.flow

class GetBanner(private val traveliRepo: TraveliRepository) {
    operator fun invoke() = flow {
        emit(DataState.Loading)
        emit(traveliRepo.getBanner())
    }
}
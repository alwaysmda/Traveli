package domain.usecase.travel

import data.remote.DataState
import domain.repository.TraveliRepository
import kotlinx.coroutines.flow.flow

class GetBannerUseCase(private val traveliRepo: TraveliRepository) {
    operator fun invoke() = flow {
        emit(DataState.Loading)
        emit(traveliRepo.getBanner())
    }
}
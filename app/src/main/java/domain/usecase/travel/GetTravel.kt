package domain.usecase.travel

import data.remote.DataState
import domain.repository.TraveliRepository
import kotlinx.coroutines.flow.flow

class GetTravel(private val repo: TraveliRepository) {

    operator fun invoke() = flow {
        emit(DataState.Loading)
        emit(repo.getTravel())
    }
}
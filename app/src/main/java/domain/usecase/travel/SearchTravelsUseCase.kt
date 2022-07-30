package domain.usecase.travel

import data.remote.DataState
import domain.repository.TraveliRepository
import kotlinx.coroutines.flow.flow

class SearchTravelsUseCase(private val repo: TraveliRepository) {

    operator fun invoke(query: String, page: Int = 1) = flow {
        emit(DataState.Loading)
        emit(repo.searchTravel(query, page))
    }
}
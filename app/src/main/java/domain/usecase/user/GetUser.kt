package domain.usecase.user

import data.remote.DataState
import domain.repository.TraveliRepository
import kotlinx.coroutines.flow.flow

class GetUser(private val repo: TraveliRepository) {


    operator fun invoke(query: String) = flow {
        emit(DataState.Loading)
        emit(repo.getUsers(query))
    }


}
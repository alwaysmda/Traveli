package domain.usecase.user

import data.remote.DataState
import domain.repository.UserRepository
import kotlinx.coroutines.flow.flow

class GetUser(private val repo: UserRepository) {
    operator fun invoke(query: String) = flow {
        emit(DataState.Loading)
        emit(repo.getUsers(query))
    }
}
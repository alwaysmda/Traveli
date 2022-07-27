package domain.usecase.user

import data.remote.DataState
import domain.repository.UserRepository
import kotlinx.coroutines.flow.flow

class SearchUserUseCase(private val repo: UserRepository) {
    operator fun invoke(query: String, page: Int) = flow {
        emit(DataState.Loading)
        emit(repo.searchUser(query, page))
    }
}
package domain.usecase.user

import data.remote.DataState
import domain.repository.UserRepository
import kotlinx.coroutines.flow.flow

class GetMe(private val repo: UserRepository) {
    operator fun invoke() = flow {
        emit(DataState.Loading)
        emit(repo.getMe())
    }
}
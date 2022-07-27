package domain.usecase.user

import data.remote.DataState
import domain.repository.UserRepository
import kotlinx.coroutines.flow.flow

class GetUserStatUseCase(private val repo: UserRepository) {
    operator fun invoke(userId: Long) = flow {
        emit(DataState.Loading)
        emit(repo.getUserStat(userId))
    }
}
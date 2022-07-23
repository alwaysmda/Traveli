package domain.usecase.user

import data.remote.DataState
import domain.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class GetUserStat(private val repo: UserRepository) {
    operator fun invoke(userId: Long) = flow {
        emit(DataState.Loading)
        delay(1000)
        emit(repo.getUserStat(userId))
    }
}
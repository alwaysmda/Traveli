package domain.usecase.user

import data.remote.DataState
import domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import main.ApplicationClass
import util.PrefManager
import util.extension.authorize

class GetMeUseCase(private val app: ApplicationClass, private val repo: UserRepository, private val prefManager: PrefManager) {
    operator fun invoke() = flow {
        emit(DataState.Loading)
        prefManager.authorize({
            emit(repo.getMe())
        }, {
            emit(DataState.Failure(DataState.Failure.CODE_NOT_FOUND, app.m.pleaseLoginToDoThisAction))
        })
    }
}
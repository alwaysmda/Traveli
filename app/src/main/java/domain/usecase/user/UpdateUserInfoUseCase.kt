package domain.usecase.user

import data.remote.DataState
import domain.model.User
import domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import main.ApplicationClass
import util.PrefManager
import util.extension.authorize

class UpdateUserInfoUseCase(private val app: ApplicationClass, private val repo: UserRepository, private val prefManager: PrefManager) {
    operator fun invoke(user: User) = flow {
        emit(DataState.Loading)
        prefManager.authorize({
            emit(repo.updateUserInfo(user))
        }, {
            emit(DataState.Failure(DataState.Failure.CODE_NOT_FOUND, app.m.pleaseLoginToDoThisAction))
        })
    }
}
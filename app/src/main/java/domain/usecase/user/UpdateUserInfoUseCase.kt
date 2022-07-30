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
            //check for length and empty values
            when (val response = repo.updateUserInfo(user)) {
                is DataState.Loading -> emit(response)
                is DataState.Failure -> emit(response)
                is DataState.Success -> {
                    app.user = response.data
                    emit(response)
                }
            }
        }, {
            emit(DataState.Failure(DataState.Failure.CODE_NOT_FOUND, app.m.pleaseLoginToDoThisAction))
        })
    }
}
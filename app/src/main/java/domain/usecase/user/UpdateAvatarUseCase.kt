package domain.usecase.user

import data.remote.DataState
import domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import main.ApplicationClass
import util.PrefManager
import util.extension.authorize

class UpdateAvatarUseCase(private val app: ApplicationClass, private val repo: UserRepository, private val prefManager: PrefManager) {
    operator fun invoke(filePath: String?) = flow {
        emit(DataState.Loading)
        prefManager.authorize({
            filePath?.let {
                emit(repo.updateAvatar(it))
            } ?: kotlin.run {
                emit(DataState.Failure(DataState.Failure.CODE_INVALID, app.m.fileIsInvalid))
            }
        }, {
            emit(DataState.Failure(DataState.Failure.CODE_NOT_FOUND, app.m.pleaseLoginToDoThisAction))
        })
    }
}
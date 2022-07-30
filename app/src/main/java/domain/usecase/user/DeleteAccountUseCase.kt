package domain.usecase.user

import data.remote.DataState
import domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import main.ApplicationClass
import util.Constant
import util.PrefManager
import util.extension.authorize

class DeleteAccountUseCase(private val app: ApplicationClass, private val repo: UserRepository, private val prefManager: PrefManager) {
    operator fun invoke() = flow {
        emit(DataState.Loading)
        prefManager.authorize({
            app.user = null
            prefManager.setPref(Constant.PREF_TOKEN, null)
            emit(repo.deleteAccount())
        }, {
            emit(DataState.Failure(DataState.Failure.CODE_NOT_FOUND, app.m.pleaseLoginToDoThisAction))
        })
    }
}
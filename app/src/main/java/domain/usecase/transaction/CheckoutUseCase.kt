package domain.usecase.transaction

import data.remote.DataState
import domain.repository.TransactionRepository
import kotlinx.coroutines.flow.flow
import main.ApplicationClass
import util.PrefManager
import util.extension.authorize

class CheckoutUseCase(private val app: ApplicationClass, private val repo: TransactionRepository, private val prefManager: PrefManager) {
    operator fun invoke() = flow {
        emit(DataState.Loading)
        prefManager.authorize({
            emit(repo.checkout())
        }) {
            emit(DataState.Failure(DataState.Failure.CODE_NOT_FOUND, app.m.pleaseLoginToDoThisAction))
        }
    }
}
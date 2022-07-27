package domain.usecase.transaction

import data.remote.DataState
import domain.repository.TransactionRepository
import kotlinx.coroutines.flow.flow
import main.ApplicationClass
import util.PrefManager
import util.extension.authorize

class ChargeUseCase(private val app: ApplicationClass, private val repo: TransactionRepository, private val prefManager: PrefManager) {
    operator fun invoke(amount: Long) = flow {
        emit(DataState.Loading)
        prefManager.authorize({
            emit(repo.charge(amount))
        }) {
            emit(DataState.Failure(DataState.Failure.CODE_NOT_FOUND, app.m.pleaseLoginToDoThisAction))
        }
    }
}
package domain.usecase.transaction

import data.remote.DataState
import domain.repository.TransactionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import main.ApplicationClass
import ui.transaction.transactionResponseCounter
import util.PrefManager
import util.extension.authorize

class GetTransactionListUseCase(private val app: ApplicationClass, private val repo: TransactionRepository, private val prefManager: PrefManager) {
    operator fun invoke(page: Int) = flow {
        emit(DataState.Loading)
        prefManager.authorize({
            //emit(repo.getTransaction(page)) //todo uncomment
            //todo remove when
            when (transactionResponseCounter) {
                1, 2 -> {
                    delay(1000)
                    transactionResponseCounter++
                    emit(DataState.Failure(0, "No Internet connection"))
                }
                3, 4 -> {
                    transactionResponseCounter++
                    emit(repo.getTransaction(page))
                }
                5, 6 -> {
                    delay(1000)
                    transactionResponseCounter++
                    emit(DataState.Failure(0, "Something went wrong"))
                }
                else -> {
                    transactionResponseCounter++
                    emit(repo.getTransaction(page))
                }
            }
        }) {
            emit(DataState.Failure(DataState.Failure.CODE_NOT_FOUND, app.m.pleaseLoginToDoThisAction))
        }
    }
}
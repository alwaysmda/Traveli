package domain.usecase.transaction

import data.remote.DataState
import domain.repository.TransactionRepository
import kotlinx.coroutines.flow.flow
import main.ApplicationClass

class GetChargePriceListUseCase(private val app: ApplicationClass, private val repo: TransactionRepository) {
    operator fun invoke() = flow {
        emit(DataState.Loading)
        emit(repo.getChargePriceList())
    }
}
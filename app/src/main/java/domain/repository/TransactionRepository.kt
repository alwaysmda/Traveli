package domain.repository

import data.remote.DataState
import domain.model.DataTransaction

interface TransactionRepository {
    suspend fun getTransaction(page: Int): DataState<DataTransaction>
    suspend fun charge(amount: Long): DataState<DataTransaction>
    suspend fun checkout(): DataState<DataTransaction>
}
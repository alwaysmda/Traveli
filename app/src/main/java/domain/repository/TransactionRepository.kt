package domain.repository

import data.remote.DataState
import domain.model.Balance
import domain.model.ChargePrice
import domain.model.TransactionData

interface TransactionRepository {
    suspend fun getBalance(): DataState<Balance>
    suspend fun getTransaction(page: Int): DataState<TransactionData>
    suspend fun charge(amount: Long): DataState<Balance>
    suspend fun checkout(): DataState<Balance>
    suspend fun getChargePriceList(): DataState<ChargePrice>
}
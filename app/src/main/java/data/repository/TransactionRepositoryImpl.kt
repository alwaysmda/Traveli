package data.repository

import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.TransactionApi
import data.remote.dto.*
import domain.model.Balance
import domain.model.TransactionData
import domain.repository.TransactionRepository
import main.ApplicationClass

class TransactionRepositoryImpl(
    app: ApplicationClass,
    networkErrorMapper: NetworkErrorMapper,
    private val transactionApi: TransactionApi,
    private val balanceMapper: BalanceMapper,
    private val transactionDataMapper: TransactionDataMapper
) : TransactionRepository, ApiResponseHandler(app, networkErrorMapper) {
    override suspend fun getBalance(): DataState<Balance> {
        return when (val response = call { transactionApi.getBalance() }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseBalanceDto.getFake()
                DataState.Success(balanceMapper.toDomainModel(data))
            }
        }
    }

    override suspend fun getTransaction(page: Int): DataState<TransactionData> {
        return when (val response = call { transactionApi.getTransactions(page) }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseTransactionDto.getFake()
                DataState.Success(transactionDataMapper.toDomainModel(data.data))
            }
        }
    }

    override suspend fun charge(amount: Long): DataState<Balance> {
        return when (val response = call { transactionApi.charge(amount) }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseBalanceDto.getFake()
                DataState.Success(balanceMapper.toDomainModel(data))
            }
        }
    }

    override suspend fun checkout(): DataState<Balance> {
        return when (val response = call { transactionApi.checkout() }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseBalanceDto.getFake()
                DataState.Success(balanceMapper.toDomainModel(data))
            }
        }
    }
}
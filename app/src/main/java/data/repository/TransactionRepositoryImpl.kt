package data.repository

import data.remote.ApiResponseHandler
import data.remote.DataState
import data.remote.TransactionApi
import data.remote.dto.DataDataTransactionMapper
import data.remote.dto.NetworkErrorMapper
import data.remote.dto.ResponseTransactionDto
import domain.model.DataTransaction
import domain.repository.TransactionRepository
import main.ApplicationClass

class TransactionRepositoryImpl(
    app: ApplicationClass,
    networkErrorMapper: NetworkErrorMapper,
    private val transactionApi: TransactionApi,
    private val dataTransactionMapper: DataDataTransactionMapper,
) : TransactionRepository, ApiResponseHandler(app, networkErrorMapper) {
    override suspend fun getTransaction(page: Int): DataState<DataTransaction> {
        return when (val response = call { transactionApi.getTransactions(page) }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseTransactionDto.getFake()
                DataState.Success(dataTransactionMapper.toDomainModel(data.dataTransactionDto))
            }
        }
    }

    override suspend fun charge(amount: Long): DataState<DataTransaction> {
        return when (val response = call { transactionApi.charge(amount) }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseTransactionDto.getFake()
                DataState.Success(dataTransactionMapper.toDomainModel(data.dataTransactionDto))
            }
        }
    }

    override suspend fun checkout(): DataState<DataTransaction> {
        return when (val response = call { transactionApi.checkout() }) {
            is DataState.Failure -> response
            is DataState.Success -> DataState.Loading
            is DataState.Loading -> {
                val data = ResponseTransactionDto.getFake()
                DataState.Success(dataTransactionMapper.toDomainModel(data.dataTransactionDto))
            }
        }
    }
}
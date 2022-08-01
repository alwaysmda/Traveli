package data.remote

import data.remote.dto.ResponseBalanceDto
import data.remote.dto.ResponseTransactionDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface TransactionApi {
    @GET("transactions/balance")
    suspend fun getBalance(): Response<ResponseBalanceDto>

    @GET("transactions")
    suspend fun getTransactions(@Query("page") page: Int): Response<ResponseTransactionDto>

    @GET("transactions/charge")
    suspend fun charge(@Field("amount") amount: Long): Response<ResponseTransactionDto>

    @GET("transactions/checkout")
    suspend fun checkout(): Response<ResponseTransactionDto>
}
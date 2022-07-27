package data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class DataTransactionDto(
    @SerializedName("balance") val balance: Long,
    @SerializedName("data") val transactionList: List<TransactionDto>,
) {
    companion object {
        fun getFake(): DataTransactionDto = DataTransactionDto(Random.nextLong(1, 1000) * 100, TransactionDto.getFakeList())
    }
}

package data.remote.dto

import com.google.gson.annotations.SerializedName

data class TransactionDataDto(
    @SerializedName("has_more") val hasMore: Boolean,
    @SerializedName("data") val data: List<TransactionDto>,
) {
    companion object {
        fun getFake(): TransactionDataDto = TransactionDataDto(true, TransactionDto.getFakeList())
    }
}

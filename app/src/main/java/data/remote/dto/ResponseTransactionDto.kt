package data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponseTransactionDto(
    @SerializedName("status") val status: String,
    @SerializedName("meta") val meta: MetaDto,
    @SerializedName("data") val dataTransactionDto: DataTransactionDto,
) {
    companion object {
        fun getFake(): ResponseTransactionDto = ResponseTransactionDto("success", MetaDto.getFake(), DataTransactionDto.getFake())
    }
}

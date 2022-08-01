package data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class ResponseBalanceDto(
    @SerializedName("status") val status: String,
    @SerializedName("meta") val meta: MetaDto,
    @SerializedName("data") val balance: Long,
) {
    companion object {
        fun getFake(): ResponseBalanceDto = ResponseBalanceDto("success", MetaDto.getFake(), Random.nextLong(1000, 10000) * 10)
    }
}

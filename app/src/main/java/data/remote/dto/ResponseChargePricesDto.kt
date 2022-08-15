package data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponseChargePricesDto(
    @SerializedName("status") val status: String,
    @SerializedName("meta") val meta: MetaDto,
    @SerializedName("data") val data: ChargePriceDto,
) {
    companion object {
        fun getFake(): ResponseChargePricesDto = ResponseChargePricesDto("success", MetaDto.getFake(), ChargePriceDto.getFake())
    }
}

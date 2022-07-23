package data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponseStatDto(
    @SerializedName("status") val status: String,
    @SerializedName("meta") val meta: MetaDto,
    @SerializedName("data") val statList: ArrayList<StatDto>,
) {
    companion object {
        fun getFake(): ResponseStatDto = ResponseStatDto("success", MetaDto.getFake(), StatDto.getFakeList())
    }
}

package data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponseBookmarkDto(
    @SerializedName("status") val status: String,
    @SerializedName("meta") val meta: MetaDto,
    @SerializedName("data") val travelDto: TravelDto,
) {
    companion object {
        fun getFake() = ResponseBookmarkDto("success", MetaDto.getFake(), TravelDto.getFake())
    }
}

package data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponseTravelListDto(
    @SerializedName("status") val status: String,
    @SerializedName("meta") val meta: MetaDto,
    @SerializedName("data") val travels: ArrayList<TravelPreviewDto>,
) {

    companion object {
        fun getFake() = ResponseTravelListDto("success", MetaDto.getFake(), TravelPreviewDto.getFake(10))
    }

}

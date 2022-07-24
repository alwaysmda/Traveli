package data.remote.dto.travelDetail

import com.google.gson.annotations.SerializedName
import data.remote.dto.MetaDto

data class ResponseTravelDetailDto(
    @SerializedName("status") val status: String,
    @SerializedName("meta") val meta: MetaDto,
    @SerializedName("data") val travelDetailDto: List<TravelDetailDto>,
) {


    companion object {
        fun getFake() = ResponseTravelDetailDto("success", MetaDto.getFake(), TravelDetailDto.getFakes())
    }


}

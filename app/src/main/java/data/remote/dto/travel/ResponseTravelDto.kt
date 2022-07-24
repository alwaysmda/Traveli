package data.remote.dto.travel

import com.google.gson.annotations.SerializedName
import data.remote.dto.MetaDto

data class ResponseTravelDto(
    @SerializedName("status") val status: String,
    @SerializedName("meta") val meta: MetaDto,
    @SerializedName("data") val travels: ArrayList<TravelDto>,
) {

    companion object {
        fun getFake() = ResponseTravelDto("success", MetaDto.getFake(), TravelDto.getFake(10))
    }

}

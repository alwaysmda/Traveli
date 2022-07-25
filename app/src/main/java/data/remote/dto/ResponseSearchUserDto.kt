package data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponseSearchUserDto(
    @SerializedName("status") val status: String,
    @SerializedName("meta") val meta: MetaDto,
    @SerializedName("data") val userPreviewList: List<UserPreviewDto>,
) {
    companion object {
        fun getFake() = ResponseSearchUserDto("success", MetaDto.getFake(), UserPreviewDto.getFakeList())
    }
}

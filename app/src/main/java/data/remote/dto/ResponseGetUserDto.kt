package data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResponseGetUserDto(
    @SerializedName("status") val status: String,
    @SerializedName("meta") val meta: MetaDto,
    @SerializedName("data") val user: UserDto,
) {
    companion object {
        fun getFake(userId: Long? = null) = ResponseGetUserDto("success", MetaDto.getFake(), UserDto.getFake(userId))
    }
}

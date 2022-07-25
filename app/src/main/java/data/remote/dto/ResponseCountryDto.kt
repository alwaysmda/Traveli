package data.remote.dto

import com.google.gson.annotations.SerializedName


data class ResponseCountryDto(
    @SerializedName("status") val status: String,
    @SerializedName("meta") val meta: MetaDto,
    @SerializedName("data") val countries: ArrayList<CountryDto>,
) {
    companion object {
        fun getFake() = ResponseCountryDto("success", MetaDto.getFake(), CountryDto.getFakes())


    }


}

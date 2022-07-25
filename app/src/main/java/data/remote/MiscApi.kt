package data.remote

import data.remote.dto.ResponseCountryDto
import retrofit2.Response
import retrofit2.http.GET

interface MiscApi {
    @GET("countries")
    suspend fun getCountries(): Response<ResponseCountryDto>
}
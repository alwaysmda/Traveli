package data.remote

import data.remote.dto.country.ResponseCountryDto
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("countries")
    suspend fun getCountries(): Response<ResponseCountryDto>


}
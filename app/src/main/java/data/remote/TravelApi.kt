package data.remote

import data.remote.dto.travel.ResponseTravelDto
import data.remote.dto.travelDetail.ResponseTravelDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelApi {
    @GET("travels/trending")
    suspend fun getTrending(): Response<ResponseTravelDto>

    @GET("travels/new")
    suspend fun getNewTravel(): Response<ResponseTravelDto>

    @GET("travels")
    suspend fun getTravel(@Query("query") travel: String): Response<ResponseTravelDto>

    @GET("travels/banner")
    suspend fun getBanner(): Response<Unit>

    @GET("countries")
    suspend fun getCountries(): Response<Unit>

    @GET("travels/{travelId}")
    suspend fun getTravelDetail(@Path("travelId") travelId: Int): Response<ResponseTravelDetailDto>
}
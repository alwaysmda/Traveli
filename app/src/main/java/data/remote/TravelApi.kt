package data.remote

import data.remote.dto.ResponseTravelDetailDto
import data.remote.dto.ResponseTravelListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelApi {
    @GET("travels/trending")
    suspend fun getTrending(): Response<ResponseTravelListDto>

    @GET("travels/new")
    suspend fun getNewTravel(): Response<ResponseTravelListDto>

    @GET("travels")
    suspend fun getTravel(@Query("query") travel: String): Response<ResponseTravelListDto>

    @GET("travels/banner")
    suspend fun getBanner(): Response<Unit>

    @GET("travels/{travelId}")
    suspend fun getTravelDetail(@Path("travelId") travelId: Int): Response<ResponseTravelDetailDto>
}
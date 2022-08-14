package data.remote

import data.remote.dto.ResponseBookmarkDto
import data.remote.dto.ResponseTravelDetailDto
import data.remote.dto.ResponseTravelListDto
import retrofit2.Response
import retrofit2.http.*

interface TravelApi {
    @GET("travels/trending")
    suspend fun getTrending(): Response<ResponseTravelListDto>

    @GET("travels/new/{page}")
    suspend fun getNewTravels(@Path("page") page: Int): Response<ResponseTravelListDto>

    @GET("travels/{travelType}/{page}")
    suspend fun getTravelByType(@Path("travelType") travelType: String, page: Int): Response<ResponseTravelListDto>

    @GET("travels")
    suspend fun getTravels(@Query("query") travel: String, @Query("page") page: Int): Response<ResponseTravelListDto>

    @GET("travels/banner")
    suspend fun getBanner(): Response<Unit>

    @GET("travels/{travelId}")
    suspend fun getTravelDetail(@Path("travelId") travelId: Long): Response<ResponseTravelDetailDto>


    @FormUrlEncoded
    @POST("travels/{travelId}/bookMark")
    suspend fun bookMarkTravel(
        @Field("isBookMark") isBookMark: Boolean
    ): Response<ResponseBookmarkDto>
}
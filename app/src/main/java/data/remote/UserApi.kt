package data.remote

import data.remote.dto.ResponseGetUserDto
import data.remote.dto.ResponseSearchUserDto
import data.remote.dto.ResponseStatDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("users")
    suspend fun searchUser(@Query("query") query: String, @Query("page") page: Int): Response<ResponseSearchUserDto>

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Long): Response<ResponseGetUserDto>

    @GET("users/{userId}/stat")
    suspend fun getUserStat(@Path("userId") userId: Long): Response<ResponseStatDto>

    @GET("users/{userId}/travels")
    suspend fun getUserTravelList(@Path("userId") userId: Long): Response<ResponseStatDto>
}
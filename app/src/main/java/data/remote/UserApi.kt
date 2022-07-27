package data.remote

import data.remote.dto.ResponseGetUserDto
import data.remote.dto.ResponseSearchUserDto
import data.remote.dto.ResponseStatDto
import data.remote.dto.ResponseTravelListDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @GET("users")
    suspend fun searchUser(@Query("query") query: String, @Query("page") page: Int): Response<ResponseSearchUserDto>

    @GET("users/me")
    suspend fun getMe(): Response<ResponseGetUserDto>

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Long): Response<ResponseGetUserDto>

    @GET("users/{userId}/stat")
    suspend fun getUserStat(@Path("userId") userId: Long): Response<ResponseStatDto>

    @GET("users/{userId}/travels")
    suspend fun getUserTravelList(@Path("userId") userId: Long, page: Int): Response<ResponseTravelListDto>

    @Multipart
    @POST("users/update/cover")
    suspend fun updateCover(@Part("cover") cover: MultipartBody.Part): Response<ResponseGetUserDto>

    @Multipart
    @POST("users/update/avatar")
    suspend fun updateAvatar(@Part("avatar") avatar: MultipartBody.Part): Response<ResponseGetUserDto>

    @POST("users/update/info")
    suspend fun updateUserInfo(
        @Field("name") name: String,
        @Field("bio") bio: String,
        @Field("countryId") countryId: Long,
        @Field("city") city: String,
    ): Response<ResponseGetUserDto>

    @POST("users/update/contact")
    suspend fun updateContact(
        @Field("title") title: String,
        @Field("value") value: String?,
    ): Response<ResponseGetUserDto>
}
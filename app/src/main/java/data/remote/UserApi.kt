package data.remote

import data.remote.dto.ResponseStatDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import util.Constant.CON_BASE_TEMPLATE_URL

interface UserApi {
    @GET(CON_BASE_TEMPLATE_URL)
    suspend fun getUsers(): Response<Unit>

    @GET("users/{userId}")
    suspend fun getUserStat(@Path("userId") userId: Long): Response<ResponseStatDto>
}
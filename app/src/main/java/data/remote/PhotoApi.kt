package data.remote

import data.remote.dto.PhotoDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface PhotoApi {
    @Streaming
    @GET
    suspend fun download(@Url url: String): Response<ResponseBody>

    @POST("post")
    suspend fun post(@FieldMap body: Map<String, String>): Response<String>

    @GET("albums/{page}/photos")
    suspend fun getPhotoList(@Path(value = "page", encoded = true) page: Int): Response<ArrayList<PhotoDto>>

    @GET("photosx/{photo_id}")
    suspend fun getPhoto(@Path(value = "photo_id", encoded = true) photoId: Int): Response<PhotoDto>

    @GET
    suspend fun getClient(
        @Url url: String = "https://chat-node.cslsrvr.com/clients",
    ): Response<ArrayList<String>>

    @GET
    suspend fun getRooms(
        @Url url: String,
    ): Response<ArrayList<String>>

    @GET
    suspend fun getRoomsUsers(
        @Url url: String,
    ): Response<ArrayList<String>>
}
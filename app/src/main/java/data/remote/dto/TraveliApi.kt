package data.remote.dto

import retrofit2.Response
import retrofit2.http.GET

const val GOOGLE_URL = "https://www.google.com"

interface TraveliApi {


    @GET(GOOGLE_URL)
    suspend fun getTravel(): Response<Unit>


    @GET(GOOGLE_URL)
    suspend fun getBanner(): Response<Unit>


}
package data.remote

import retrofit2.Response
import retrofit2.http.GET
import util.Constant.CON_BASE_TEMPLATE_URL

interface TraveliApi {
    @GET(CON_BASE_TEMPLATE_URL)
    suspend fun getTrending(): Response<Unit>

    @GET(CON_BASE_TEMPLATE_URL)
    suspend fun getNewTravel(): Response<Unit>

    @GET(CON_BASE_TEMPLATE_URL)
    suspend fun getTravel(): Response<Unit>

    @GET(CON_BASE_TEMPLATE_URL)
    suspend fun getBanner(): Response<Unit>

    @GET(CON_BASE_TEMPLATE_URL)
    suspend fun getCountries(): Response<Unit>

    @GET(CON_BASE_TEMPLATE_URL)
    suspend fun getTravelDetail(travelId: Int): Response<Unit>
}
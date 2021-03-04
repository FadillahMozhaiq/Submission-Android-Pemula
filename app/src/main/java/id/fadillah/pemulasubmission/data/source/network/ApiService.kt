package id.fadillah.pemulasubmission.data.source.network

import id.fadillah.pemulasubmission.data.source.network.reponse.MangaDetailResponse
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaResponses
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("recommended")
    fun getMangas(): Call<MangaResponses>

    @GET("manga/detail/{endPoint}")
    fun getDetailManga(
        @Path("endPoint") endPoint: String
    ): Call<MangaDetailResponse>
}
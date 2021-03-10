package id.fadillah.pemulasubmission.data.source.network

import android.util.Log
import id.fadillah.pemulasubmission.data.source.network.ApiConfig.Companion.getApiService
import id.fadillah.pemulasubmission.data.source.network.reponse.ChapterResponse
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaDetailResponse
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaListItem
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaResponses
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    companion object {
        private val TAG = RemoteDataSource::class.java.simpleName

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getAllManga(callback: LoadMangaCallback) {

        val client = getApiService().getMangas()

        client.enqueue(object : Callback<MangaResponses> {
            override fun onResponse(
                call: Call<MangaResponses>,
                response: Response<MangaResponses>
            ) {
                if (response.isSuccessful) {
                    val mangas = response.body()?.mangaList
                    if (mangas != null) {
                        callback.onAllMangaReceived(mangas)
                    }
                } else {
                    Log.e(TAG, "Error Get Data: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MangaResponses>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getDetailManga(endpoint: String, callback: LoadDetailMangaCallback) {

        val client = getApiService().getDetailManga(endpoint)

        client.enqueue(object : Callback<MangaDetailResponse> {
            override fun onResponse(
                call: Call<MangaDetailResponse>,
                response: Response<MangaDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val detailManga = response.body()
                    detailManga?.let {
                        callback.onDetailMangaReceived(it)
                    }
                } else {
                    Log.e(TAG, "Failed Get Data: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MangaDetailResponse>, t: Throwable) {
                Log.e(TAG, "On Failure: ${t.message}")
            }
        })
    }

    fun getQuestManga(query: String, callback: LoadQuestMangaCallback) {
        val client = getApiService().getQuestManga(query)

        client.enqueue(object : Callback<MangaResponses> {
            override fun onResponse(
                call: Call<MangaResponses>,
                response: Response<MangaResponses>
            ) {
                if (response.isSuccessful) {
                    val mangas = response.body()?.mangaList
                    if (mangas != null) {
                        callback.onAllQuestMangaReceived(mangas)
                    }
                } else {
                    Log.e(TAG, "Error Get Data: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MangaResponses>, t: Throwable) {
                Log.e(TAG, "On Failure: ${t.message}")
            }

        })
    }

    fun getMangaChapters(endpoint: String, callback: LoadChaptersCallback) {
        val client = getApiService().getChapters(endpoint)

        client.enqueue(object : Callback<ChapterResponse> {
            override fun onResponse(
                call: Call<ChapterResponse>,
                response: Response<ChapterResponse>
            ) {
                if (response.isSuccessful) {
                    val chapters = response.body()
                    chapters?.let {
                        callback.onAllChaptesReceived(it)
                    }
                } else {
                    Log.e(TAG, "Failed Get Data: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ChapterResponse>, t: Throwable) {
                Log.e(TAG, "On Failure: ${t.message}")
            }
        })

    }

    interface LoadMangaCallback {
        fun onAllMangaReceived(mangaResponse: List<MangaListItem>)
    }

    interface LoadDetailMangaCallback {
        fun onDetailMangaReceived(mangaDetailResponse: MangaDetailResponse)
    }

    interface LoadQuestMangaCallback {
        fun onAllQuestMangaReceived(mangaResponse: List<MangaListItem>)
    }

    interface LoadChaptersCallback {
        fun onAllChaptesReceived(chapterResponse: ChapterResponse)
    }
}
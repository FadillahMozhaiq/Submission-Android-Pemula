package id.fadillah.pemulasubmission.data.source.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.fadillah.pemulasubmission.data.source.network.ApiConfig.Companion.getApiService
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaDetailResponse
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaListItem
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaResponses
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun getDetailManga(endpoint: String): LiveData<MangaDetailResponse> {
        val resultDetailManga = MutableLiveData<MangaDetailResponse>()

        CoroutineScope(Dispatchers.Default).launch {
            val client = getApiService().getDetailManga(endpoint)

            client.enqueue(object : Callback<MangaDetailResponse> {
                override fun onResponse(
                    call: Call<MangaDetailResponse>,
                    response: Response<MangaDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val detailManga = response.body()
                        detailManga?.let {
                            resultDetailManga.postValue(it)
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
        return resultDetailManga
    }

    interface LoadMangaCallback {
        fun onAllMangaReceived(mangaResponse: List<MangaListItem>)
    }
}
package id.fadillah.pemulasubmission.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.fadillah.pemulasubmission.data.model.MangaEntity
import id.fadillah.pemulasubmission.data.source.network.RemoteDataSource
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaListItem

class MangaRepository private constructor(private val remoteDataSource: RemoteDataSource): MangaDataSource {
    companion object {
        @Volatile
        private var instance: MangaRepository? = null

        fun getInstance(remoteData: RemoteDataSource): MangaRepository =
            instance ?: synchronized(this) {
                instance ?: MangaRepository(remoteData)
            }
    }
    override fun getAllManga(): LiveData<List<MangaEntity>> {
        val mangaResult = MutableLiveData<List<MangaEntity>>()

        remoteDataSource.getAllManga(object : RemoteDataSource.LoadMangaCallback {
            override fun onAllMangaReceived(mangaResponse: List<MangaListItem>) {
                val listManga = ArrayList<MangaEntity>()
                for (item in mangaResponse) {
                    listManga.add(
                        MangaEntity(item.title, item.endpoint, item.thumb)
                    )
                }
                mangaResult.postValue(listManga)
                Log.d("REMOTE", "Data $listManga")
            }
        })
        return mangaResult
//        return ConverterHelper.responsesToListEntity(dataResponse)
    }

    override fun getDetailManga(endpoint: String): LiveData<MangaEntity> {
        TODO("Not yet implemented")
    }
}
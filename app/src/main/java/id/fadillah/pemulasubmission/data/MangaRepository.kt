package id.fadillah.pemulasubmission.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.fadillah.pemulasubmission.data.model.ChapterEntity
import id.fadillah.pemulasubmission.data.model.MangaEntity
import id.fadillah.pemulasubmission.data.source.network.RemoteDataSource
import id.fadillah.pemulasubmission.data.source.network.reponse.ChapterResponse
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaDetailResponse
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaListItem
import id.fadillah.pemulasubmission.utils.ConverterHelper

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
                val listManga = ConverterHelper.responsesToListEntity(mangaResponse)
                mangaResult.postValue(listManga)
            }
        })
        if (mangaResult.value.isNullOrEmpty()) {
            mangaResult.value = emptyList()
        }
        return mangaResult
    }

    override fun getDetailManga(endpoint: String): LiveData<MangaEntity> {
        val detailResult = MutableLiveData<MangaEntity>()

        remoteDataSource.getDetailManga(endpoint, object : RemoteDataSource.LoadDetailMangaCallback {
            override fun onDetailMangaReceived(mangaDetailResponse: MangaDetailResponse) {
                val data = ConverterHelper.detailResponseToEntity(mangaDetailResponse)
                detailResult.postValue(data)
            }
        })
        return detailResult
    }

    override fun getQuestManga(query: String): LiveData<List<MangaEntity>> {
        val mangaResult = MutableLiveData<List<MangaEntity>>()

        remoteDataSource.getQuestManga(query, object : RemoteDataSource.LoadQuestMangaCallback {
            override fun onAllQuestMangaReceived(mangaResponse: List<MangaListItem>) {
                val listManga = ConverterHelper.responsesToListEntity(mangaResponse)
                mangaResult.postValue(listManga)
            }
        })
        if (mangaResult.value.isNullOrEmpty()) {
            mangaResult.value = emptyList()
        }
        return mangaResult
    }

    override fun getChapterManga(endpoint: String): LiveData<List<ChapterEntity>> {
        val listChapter = MutableLiveData<List<ChapterEntity>>()

        remoteDataSource.getMangaChapters(endpoint, object : RemoteDataSource.LoadChaptersCallback {
            override fun onAllChaptesReceived(chapterResponse: ChapterResponse) {
                val listChapters = ConverterHelper.chapterResponseToChapterEntity(chapterResponse.chapterImage)
                listChapter.postValue(listChapters)
            }
        })
        return listChapter
    }
}
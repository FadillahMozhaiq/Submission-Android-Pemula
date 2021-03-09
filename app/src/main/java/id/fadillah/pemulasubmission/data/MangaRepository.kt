package id.fadillah.pemulasubmission.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.fadillah.pemulasubmission.data.model.ChapterEntity
import id.fadillah.pemulasubmission.data.model.MangaEntity
import id.fadillah.pemulasubmission.data.source.local.LocalDataSource
import id.fadillah.pemulasubmission.data.source.local.entity.MangaBookmarkEntity
import id.fadillah.pemulasubmission.data.source.local.entity.MangaRecommendedEntity
import id.fadillah.pemulasubmission.data.source.network.RemoteDataSource
import id.fadillah.pemulasubmission.data.source.network.reponse.ChapterResponse
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaDetailResponse
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaListItem
import id.fadillah.pemulasubmission.utils.ConverterHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MangaRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MangaDataSource {
    companion object {
        @Volatile
        private var instance: MangaRepository? = null

        fun getInstance(remoteData: RemoteDataSource, localData: LocalDataSource): MangaRepository =
            instance ?: synchronized(this) {
                instance ?: MangaRepository(remoteData, localData)
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
        return mangaResult
    }

    override fun getDetailManga(endpoint: String): LiveData<MangaEntity> {
        val detailResult = MutableLiveData<MangaEntity>()

        remoteDataSource.getDetailManga(
            endpoint,
            object : RemoteDataSource.LoadDetailMangaCallback {
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
        return mangaResult
    }

    override fun getChapterManga(endpoint: String): LiveData<List<ChapterEntity>> {
        val listChapter = MutableLiveData<List<ChapterEntity>>()

        remoteDataSource.getMangaChapters(endpoint, object : RemoteDataSource.LoadChaptersCallback {
            override fun onAllChaptesReceived(chapterResponse: ChapterResponse) {
                val listChapters =
                    ConverterHelper.chapterResponseToChapterEntity(chapterResponse.chapterImage)
                listChapter.postValue(listChapters)
            }
        })
        return listChapter
    }

    override fun getRecommendedManga(): LiveData<List<MangaEntity>> {
        val listData = MutableLiveData<List<MangaEntity>>()
        localDataSource.getAllRecommended(object : LocalDataSource.LoadRecommendedMangaCallback {
            override fun onAllMangaReceived(data: List<MangaRecommendedEntity>) {
                val listManga = ConverterHelper.listRecommendedEntityToMangaEntity(data)
                listData.postValue(listManga)
            }
        })
        return listData
    }

    override fun getBookmarkedManga(): LiveData<List<MangaEntity>> {
        val listManga = MutableLiveData<List<MangaEntity>>()

        localDataSource.getAllBookmarked(object : LocalDataSource.LoadBookmarkedMangaCallback {
            override fun onAllMangaReceived(data: LiveData<List<MangaBookmarkEntity>>) {
                data.value?.let { list ->
                    val listMangaEntity = list.map {
                        MangaEntity(it.title, it.endpoint, it.thumbnail)
                    }
                    listManga.postValue(listMangaEntity)
                }
            }
        })
        return listManga
    }

    override fun isBookmarked(endpoint: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        CoroutineScope(Dispatchers.IO).launch {
            val data = localDataSource.isBookmarked(endpoint).value
            result.postValue(data != 0)
        }
        return result
    }

    override fun insertBookmarkManga(mangaEntity: MangaEntity) {
        val data = ConverterHelper.mangaEntityToMangaBookmarkEntity(mangaEntity)
        localDataSource.insertBookmarkedManga(data)
    }

    override fun updateBookmarkManga(mangaEntity: MangaEntity) {
        val data = ConverterHelper.mangaEntityToMangaBookmarkEntity(mangaEntity)
        localDataSource.updateBookmarkedManga(data)
    }

    override fun deleteBookmarkManga(mangaEntity: MangaEntity) {
        val data = ConverterHelper.mangaEntityToMangaBookmarkEntity(mangaEntity)
        localDataSource.deleteBookmarkedManga(data)
    }
}
package id.fadillah.pemulasubmission.data.source.local

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import id.fadillah.pemulasubmission.data.source.local.entity.MangaBookmarkEntity
import id.fadillah.pemulasubmission.data.source.local.entity.MangaRecommendedEntity
import id.fadillah.pemulasubmission.data.source.local.room.MangaDao
import id.fadillah.pemulasubmission.utils.JsonHelper

class LocalDataSource private constructor(private val mMangaDao: MangaDao, private val jsonHelper: JsonHelper) {
    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(mangaDao: MangaDao, jsonHelper: JsonHelper): LocalDataSource =
            INSTANCE ?: LocalDataSource(mangaDao, jsonHelper)
    }

    private val handler = Handler(Looper.getMainLooper())

    fun getAllRecommended(callback: LoadRecommendedMangaCallback){
        handler.postDelayed({
            callback.onAllMangaReceived(jsonHelper.loadRecommendedManga())
        }, 2000L)
    }

    fun getAllBookmarked(): LiveData<List<MangaBookmarkEntity>>  = mMangaDao.getAllBookmarkManga()

    fun insertBookmarkedManga(manga: MangaBookmarkEntity) = mMangaDao.insertManga(manga)

    fun updateBookmarkedManga(manga: MangaBookmarkEntity) = mMangaDao.updateManga(manga)

    fun deleteBookmarkedManga(manga: MangaBookmarkEntity) = mMangaDao.deleteManga(manga)

    fun isBookmarked(endpoint: String): LiveData<Int> = mMangaDao.checkIsBookmarked(endpoint)

    interface LoadRecommendedMangaCallback {
        fun onAllMangaReceived(data: List<MangaRecommendedEntity>)
    }

    interface LoadBookmarkedMangaCallback {
        fun onAllMangaReceived(data: LiveData<List<MangaBookmarkEntity>>)
    }
}
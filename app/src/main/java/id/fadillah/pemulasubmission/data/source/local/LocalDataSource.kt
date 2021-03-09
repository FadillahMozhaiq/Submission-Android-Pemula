package id.fadillah.pemulasubmission.data.source.local

import id.fadillah.pemulasubmission.data.source.local.room.MangaDao

class LocalDataSource private constructor(private val mMangaDao: MangaDao) {
    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(mangaDao: MangaDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(mangaDao)
    }

    fun getAllBookmarked():
}
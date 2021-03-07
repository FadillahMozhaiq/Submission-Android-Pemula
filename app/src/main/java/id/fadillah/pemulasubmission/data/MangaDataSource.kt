package id.fadillah.pemulasubmission.data

import androidx.lifecycle.LiveData
import id.fadillah.pemulasubmission.data.model.ChapterEntity
import id.fadillah.pemulasubmission.data.model.MangaEntity

interface MangaDataSource {
    fun getAllManga(): LiveData<List<MangaEntity>>
    fun getDetailManga(endpoint: String): LiveData<MangaEntity>
    fun getQuestManga(query: String): LiveData<List<MangaEntity>>
    fun getChapterManga(endpoint: String): LiveData<List<ChapterEntity>>
}
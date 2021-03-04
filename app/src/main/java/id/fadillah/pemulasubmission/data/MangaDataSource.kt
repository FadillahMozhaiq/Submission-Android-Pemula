package id.fadillah.pemulasubmission.data

import androidx.lifecycle.LiveData
import id.fadillah.pemulasubmission.data.model.MangaEntity

interface MangaDataSource {
    fun getAllManga(): LiveData<List<MangaEntity>>
    fun getDetailManga(endpoint: String): LiveData<MangaEntity>
}
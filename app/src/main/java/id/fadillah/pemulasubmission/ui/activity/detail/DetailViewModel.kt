package id.fadillah.pemulasubmission.ui.activity.detail

import androidx.lifecycle.LiveData
import id.fadillah.pemulasubmission.data.MangaRepository
import id.fadillah.pemulasubmission.data.model.MangaEntity

class DetailViewModel(private val mangaRepository: MangaRepository) {
    fun getDetailManga(endpoint: String): LiveData<MangaEntity> = mangaRepository.getDetailManga(endpoint)
}
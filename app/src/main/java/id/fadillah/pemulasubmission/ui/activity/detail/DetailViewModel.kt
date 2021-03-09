package id.fadillah.pemulasubmission.ui.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.fadillah.pemulasubmission.data.MangaRepository
import id.fadillah.pemulasubmission.data.model.MangaEntity

class DetailViewModel(private val mangaRepository: MangaRepository): ViewModel() {
    fun getDetailManga(endpoint: String): LiveData<MangaEntity> = mangaRepository.getDetailManga(endpoint)
    fun isMangaBookmarked(endpoint: String) = mangaRepository.isBookmarked(endpoint)
    fun addToBookmark(mangaEntity: MangaEntity) = mangaRepository.insertBookmarkManga(mangaEntity)
}
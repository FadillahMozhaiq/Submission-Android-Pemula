package id.fadillah.pemulasubmission.ui.activity.chapterview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.fadillah.pemulasubmission.data.MangaRepository
import id.fadillah.pemulasubmission.data.model.ChapterEntity

class ChapterViewModel(private val mangaRepository: MangaRepository): ViewModel() {
    fun getChapter(endpoint: String): LiveData<List<ChapterEntity>> = mangaRepository.getChapterManga(endpoint)
}
package id.fadillah.pemulasubmission.ui.fragment.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.fadillah.pemulasubmission.data.MangaRepository
import id.fadillah.pemulasubmission.data.source.local.entity.MangaBookmarkEntity

class FavoriteViewModel(private val mangaRepository: MangaRepository): ViewModel() {
    fun getAllFavoriteManga(): LiveData<List<MangaBookmarkEntity>> = mangaRepository.getBookmarkedManga()
}
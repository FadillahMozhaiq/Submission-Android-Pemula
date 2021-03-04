package id.fadillah.pemulasubmission.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.fadillah.pemulasubmission.data.MangaRepository
import id.fadillah.pemulasubmission.data.model.MangaEntity

class HomeViewModel(private val mangaRepository: MangaRepository): ViewModel() {
    fun getAllManga(): LiveData<List<MangaEntity>> = mangaRepository.getAllManga()
}
package id.fadillah.pemulasubmission.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.fadillah.pemulasubmission.data.MangaRepository
import id.fadillah.pemulasubmission.di.Injection
import id.fadillah.pemulasubmission.ui.activity.chapterview.ChapterViewModel
import id.fadillah.pemulasubmission.ui.activity.detail.DetailViewModel
import id.fadillah.pemulasubmission.ui.fragment.favorite.FavoriteViewModel
import id.fadillah.pemulasubmission.ui.fragment.home.HomeViewModel

class ViewModelFactory private constructor(private val mangaRepository: MangaRepository): ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository())
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(mangaRepository) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) ->
                FavoriteViewModel(mangaRepository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) ->
                DetailViewModel(mangaRepository) as T
            modelClass.isAssignableFrom(ChapterViewModel::class.java) ->
                ChapterViewModel(mangaRepository) as T
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
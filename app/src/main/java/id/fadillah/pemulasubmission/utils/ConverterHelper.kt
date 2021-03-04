package id.fadillah.pemulasubmission.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.fadillah.pemulasubmission.data.model.MangaEntity
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaListItem

object ConverterHelper {
    fun responsesToListEntity(responses: List<MangaListItem>?): List<MangaEntity> {
        val mangaResult = ArrayList<MangaEntity>()
        responses?.let {
            for (item in it) {
                mangaResult.add(
                    MangaEntity(item.title, item.endpoint, item.thumb)
                )
            }
        }
        return mangaResult
    }

    fun detailResponseToEntity() {

    }
}
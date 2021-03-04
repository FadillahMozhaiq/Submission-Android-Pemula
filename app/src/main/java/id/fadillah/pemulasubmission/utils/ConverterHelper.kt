package id.fadillah.pemulasubmission.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.fadillah.pemulasubmission.data.model.MangaEntity
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaListItem

object ConverterHelper {
    fun responsesToListEntity(responses: LiveData<List<MangaListItem>>): LiveData<List<MangaEntity>> {
        val mangaResult = MutableLiveData<List<MangaEntity>>()
        val resultData = ArrayList<MangaEntity>()
        val data = responses.value
        data?.let {
            for (item in it) {
                resultData.add(
                    MangaEntity(item.title, item.endpoint, item.thumb)
                )
            }
            mangaResult.postValue(resultData)
        }
        return mangaResult
    }

    fun detailResponseToEntity() {

    }
}
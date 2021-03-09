package id.fadillah.pemulasubmission.utils

import android.content.Context
import id.fadillah.pemulasubmission.data.source.local.entity.MangaRecommendedEntity
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {
    private fun parsingFileToString(filename: String): String? {
        return try {
            val `is` = context.assets.open(filename)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadRecommendedManga(): List<MangaRecommendedEntity> {
        val list = ArrayList<MangaRecommendedEntity>()
        try {
            val responseObject = JSONObject(parsingFileToString("recommended_data.json").toString())
            val listArray = responseObject.getJSONArray("manga_list")
            for (i in 0 until listArray.length()) {
                val manga = listArray.getJSONObject(i)

                val title = manga.getString("title")
                val thumb = manga.getString("thumb")
                val endpoint = manga.getString("endpoint")

                val mangaResponse = MangaRecommendedEntity(title, thumb, endpoint)
                list.add(mangaResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }
}
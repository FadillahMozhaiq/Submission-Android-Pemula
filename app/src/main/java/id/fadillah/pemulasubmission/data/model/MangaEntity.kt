package id.fadillah.pemulasubmission.data.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class MangaEntity (
    val title: String,
    val endpoint: String,
    val thumbnail: String,
    val type: String? = null,
    val author: String? = null,
    val status: String? = null,
    val genreList: String? = null,
    val synopsis: String? = null,
    val bookmarked: Boolean = false
): Parcelable {
    @IgnoredOnParcel
    var listChapterEntity: List<MangaChapterEntity>? = null
}
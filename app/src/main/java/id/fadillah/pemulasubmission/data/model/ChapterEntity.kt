package id.fadillah.pemulasubmission.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChapterEntity (
    val chapterImage: String,
    val chapterNumber: Int,
    var chapterTitle: String? = null
): Parcelable
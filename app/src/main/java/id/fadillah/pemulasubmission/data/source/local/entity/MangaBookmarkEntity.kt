package id.fadillah.pemulasubmission.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mangabookmarks")
data class MangaBookmarkEntity(
    @PrimaryKey
    @ColumnInfo(name = "endpoint")
    var endpoint: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "thumbnail")
    var thumbnail: String,

    @ColumnInfo(name = "bookmarked")
    var bookmarked: Boolean = false,
)
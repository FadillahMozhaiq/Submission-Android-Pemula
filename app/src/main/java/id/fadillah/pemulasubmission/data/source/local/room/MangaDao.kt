package id.fadillah.pemulasubmission.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.fadillah.pemulasubmission.data.source.local.entity.MangaBookmarkEntity

@Dao
interface MangaDao {
    @Query("SELECT * FROM mangabookmarks WHERE bookmarked = 1")
    fun getAllBookmarkManga(): LiveData<List<MangaBookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManga(manga: MangaBookmarkEntity)

    @Update
    fun updateManga(manga: MangaBookmarkEntity)

    @Delete
    fun deleteManga(manga: MangaBookmarkEntity)
}
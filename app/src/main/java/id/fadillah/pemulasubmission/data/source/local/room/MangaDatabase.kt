package id.fadillah.pemulasubmission.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.fadillah.pemulasubmission.data.source.local.entity.MangaBookmarkEntity

@Database(
    entities = [MangaBookmarkEntity::class],
    version = 1
)
abstract class MangaDatabase: RoomDatabase() {
    abstract fun mangaDao(): MangaDao

    companion object {
        @Volatile
        private var INSTANCE: MangaDatabase? = null

        fun getInstance(context: Context): MangaDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    MangaDatabase::class.java,
                    "Mangas.db").build()
            }
    }
}
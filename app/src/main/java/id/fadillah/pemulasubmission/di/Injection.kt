package id.fadillah.pemulasubmission.di

import android.content.Context
import id.fadillah.pemulasubmission.data.MangaRepository
import id.fadillah.pemulasubmission.data.source.local.LocalDataSource
import id.fadillah.pemulasubmission.data.source.local.room.MangaDatabase
import id.fadillah.pemulasubmission.data.source.network.RemoteDataSource
import id.fadillah.pemulasubmission.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): MangaRepository {
        val database = MangaDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.mangaDao(), JsonHelper(context))

        return MangaRepository.getInstance(remoteDataSource, localDataSource)
    }
}
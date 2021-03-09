package id.fadillah.pemulasubmission.di

import android.content.Context
import id.fadillah.pemulasubmission.data.MangaRepository
import id.fadillah.pemulasubmission.data.source.network.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): MangaRepository {
        val remoteDataSource = RemoteDataSource.getInstance()

        return MangaRepository.getInstance(remoteDataSource)
    }
}
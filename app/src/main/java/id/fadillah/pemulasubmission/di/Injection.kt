package id.fadillah.pemulasubmission.di

import id.fadillah.pemulasubmission.data.MangaRepository
import id.fadillah.pemulasubmission.data.source.network.RemoteDataSource

object Injection {
    fun provideRepository(): MangaRepository {
        val remoteDataSource = RemoteDataSource.getInstance()

        return MangaRepository.getInstance(remoteDataSource)
    }
}
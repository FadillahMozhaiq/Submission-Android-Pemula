package id.fadillah.pemulasubmission.utils

import id.fadillah.pemulasubmission.data.model.ChapterEntity
import id.fadillah.pemulasubmission.data.model.MangaChapterEntity
import id.fadillah.pemulasubmission.data.model.MangaEntity
import id.fadillah.pemulasubmission.data.source.network.reponse.ChapterImageItem
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaDetailResponse
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaListItem

object ConverterHelper {
    fun responsesToListEntity(responses: List<MangaListItem>?): List<MangaEntity> {
        val mangaResult = ArrayList<MangaEntity>()
        responses?.let {
            for (item in it) {
                mangaResult.add(
                    MangaEntity(item.title, item.endpoint, item.thumb)
                )
            }
        }
        return mangaResult
    }

    fun detailResponseToEntity(response: MangaDetailResponse?): MangaEntity {
        response ?: return MangaEntity("Empty", "", "")
        val genreList = response.genreList.map { it.genreName }
        val mangaEntity = MangaEntity(
            response.title,
            response.mangaEndpoint,
            response.thumb,
            response.type,
            response.author,
            response.status,
            genreList.joinToString(),
            response.synopsis
        )
        mangaEntity.listChapterEntity = response.chapter.map { MangaChapterEntity(it.chapterTitle, it.chapterEndpoint) }
        return mangaEntity
    }

    fun chapterResponseToChapterEntity(responses: List<ChapterImageItem>?): List<ChapterEntity> {
        val chapterResult = ArrayList<ChapterEntity>()
        responses?.let {
            for (item in it) {
                chapterResult.add(
                    ChapterEntity(item.chapterImageLink, item.imageNumber)
                )
            }
        }
        return chapterResult
    }

}
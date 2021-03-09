package id.fadillah.pemulasubmission.utils

import id.fadillah.pemulasubmission.data.model.ChapterEntity
import id.fadillah.pemulasubmission.data.model.MangaChapterEntity
import id.fadillah.pemulasubmission.data.model.MangaEntity
import id.fadillah.pemulasubmission.data.source.local.entity.MangaBookmarkEntity
import id.fadillah.pemulasubmission.data.source.local.entity.MangaRecommendedEntity
import id.fadillah.pemulasubmission.data.source.network.reponse.ChapterImageItem
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaDetailResponse
import id.fadillah.pemulasubmission.data.source.network.reponse.MangaListItem

object ConverterHelper {
    fun responsesToListEntity(responses: List<MangaListItem>?): List<MangaEntity> {
        val mangaResult = ArrayList<MangaEntity>()
        responses?.map {
            mangaResult.add(
                MangaEntity(it.title, it.endpoint, it.thumb)
            )
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
        mangaEntity.listChapterEntity =
            response.chapter.map { MangaChapterEntity(it.chapterTitle, it.chapterEndpoint) }
        return mangaEntity
    }

    fun chapterResponseToChapterEntity(responses: List<ChapterImageItem>?): List<ChapterEntity> {
        val chapterResult = ArrayList<ChapterEntity>()
        responses?.map {
            chapterResult.add(
                ChapterEntity(it.chapterImageLink, it.imageNumber)
            )
        }
        return chapterResult
    }

    fun listRecommendedEntityToMangaEntity(data: List<MangaRecommendedEntity>?): List<MangaEntity> {
        val recommendedManga = ArrayList<MangaEntity>()

        data?.map {
            recommendedManga.add(MangaEntity(it.title, it.endpoint, it.thumbnail))
        }
        return recommendedManga
    }

    fun mangaEntityToMangaBookmarkEntity(data: MangaEntity, bookmarked: Boolean = true): MangaBookmarkEntity =
        MangaBookmarkEntity(data.endpoint, data.title, data.thumbnail, bookmarked)
}
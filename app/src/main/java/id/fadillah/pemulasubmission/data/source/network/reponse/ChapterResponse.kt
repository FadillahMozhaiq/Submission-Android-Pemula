package id.fadillah.pemulasubmission.data.source.network.reponse

import com.google.gson.annotations.SerializedName

data class ChapterResponse(

	@field:SerializedName("chapter_pages")
	val chapterPages: Int,

	@field:SerializedName("chapter_endpoint")
	val chapterEndpoint: String,

	@field:SerializedName("chapter_image")
	val chapterImage: List<ChapterImageItem>
)

data class ChapterImageItem(

	@field:SerializedName("image_number")
	val imageNumber: Int,

	@field:SerializedName("chapter_image_link")
	val chapterImageLink: String
)

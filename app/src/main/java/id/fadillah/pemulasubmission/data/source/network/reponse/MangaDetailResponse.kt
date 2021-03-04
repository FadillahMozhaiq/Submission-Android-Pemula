package id.fadillah.pemulasubmission.data.source.network.reponse

import com.google.gson.annotations.SerializedName

data class MangaDetailResponse(

	@field:SerializedName("genre_list")
	val genreList: List<GenreListItem>,

	@field:SerializedName("thumb")
	val thumb: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("synopsis")
	val synopsis: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("manga_endpoint")
	val mangaEndpoint: String,

	@field:SerializedName("status")
	val status: String
)

data class GenreListItem(

	@field:SerializedName("genre_name")
	val genreName: String
)

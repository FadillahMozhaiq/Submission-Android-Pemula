package id.fadillah.pemulasubmission.data.source.network.reponse

import com.google.gson.annotations.SerializedName

data class MangaResponses(

	@field:SerializedName("manga_list")
	val mangaList: List<MangaListItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class MangaListItem(

	@field:SerializedName("endpoint")
	val endpoint: String,

	@field:SerializedName("thumb")
	val thumb: String,

	@field:SerializedName("title")
	val title: String
)

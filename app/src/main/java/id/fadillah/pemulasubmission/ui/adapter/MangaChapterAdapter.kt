package id.fadillah.pemulasubmission.ui.adapter

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.fadillah.pemulasubmission.data.model.MangaChapterEntity
import id.fadillah.pemulasubmission.databinding.ItemChapterBinding

class MangaChapterAdapter : RecyclerView.Adapter<MangaChapterAdapter.ChapterViewHolder>() {
    private val listChapter = ArrayList<MangaChapterEntity>()

    fun setChapter(listChapter: List<MangaChapterEntity>?) {
        listChapter ?: return
        this.listChapter.clear()
        this.listChapter.addAll(listChapter)
    }

    class ChapterViewHolder(private val itemChapterBinding: ItemChapterBinding) :
        RecyclerView.ViewHolder(itemChapterBinding.root) {
        fun bind(mangaChapterEntity: MangaChapterEntity) {
            with(itemChapterBinding) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                    tvItemChapter.text = Html.fromHtml(
                        "<u>${mangaChapterEntity.chapterTitle}</u>",
                        Html.FROM_HTML_MODE_LEGACY
                    )
                } else {
                    tvItemChapter.text = Html.fromHtml("<u>${mangaChapterEntity.chapterTitle}</u>")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val view = ItemChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        holder.bind(listChapter[position])
    }

    override fun getItemCount(): Int = listChapter.size
}
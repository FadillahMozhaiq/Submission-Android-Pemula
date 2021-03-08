package id.fadillah.pemulasubmission.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.fadillah.pemulasubmission.R
import id.fadillah.pemulasubmission.data.model.ChapterEntity
import id.fadillah.pemulasubmission.databinding.ItemChapterImageBinding
import id.fadillah.pemulasubmission.utils.ImageHelper

class ChapterViewAdapter : RecyclerView.Adapter<ChapterViewAdapter.ChapterViewHolder>() {
    private val listChapter = ArrayList<ChapterEntity>()

    fun setChapters(chapters: List<ChapterEntity>?) {
        chapters ?: return
        listChapter.clear()
        listChapter.addAll(chapters)
    }

    class ChapterViewHolder(private val itemChapterImageBinding: ItemChapterImageBinding) :
        RecyclerView.ViewHolder(itemChapterImageBinding.root) {
        fun bind(chapterEntity: ChapterEntity) {
            with(itemChapterImageBinding) {
                ImageHelper.getImage(ivComic, chapterEntity.chapterImage, R.drawable.placeholder_loading)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val view =
            ItemChapterImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        holder.bind(listChapter[position])
    }

    override fun getItemCount(): Int = listChapter.size
}
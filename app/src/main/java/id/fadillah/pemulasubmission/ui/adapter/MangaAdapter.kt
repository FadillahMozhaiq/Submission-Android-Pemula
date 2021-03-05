package id.fadillah.pemulasubmission.ui.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.fadillah.pemulasubmission.data.model.MangaEntity
import id.fadillah.pemulasubmission.databinding.ItemMangaBinding
import id.fadillah.pemulasubmission.ui.activity.detail.DetailActivity
import id.fadillah.pemulasubmission.ui.activity.detail.DetailActivity.Companion.EXTRA_BUNDLE
import id.fadillah.pemulasubmission.ui.activity.detail.DetailActivity.Companion.EXTRA_DATA
import id.fadillah.pemulasubmission.utils.ImageHelper

class MangaAdapter : RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {
    private val listManga = ArrayList<MangaEntity>()

    fun setData(listMangaEntity: List<MangaEntity>?) {
        listMangaEntity ?: return
        listManga.clear()
        listManga.addAll(listMangaEntity)
    }

    class MangaViewHolder(private val binding: ItemMangaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(manga: MangaEntity) {
            binding.also { item ->
                ImageHelper.getImage(item.ivImageItem, manga.thumbnail)
                item.tvItemTitle.text = manga.title
                item.itemContent.setOnClickListener {
                    val intent = Intent(it.context, DetailActivity::class.java).apply {
                        val bundle = Bundle().apply {
                            putParcelable(
                                EXTRA_DATA,
                                MangaEntity(manga.title, manga.endpoint, manga.thumbnail)
                            )
                        }
                        putExtra(EXTRA_BUNDLE, bundle)
                    }
                    it.context.startActivity(intent)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val itemBinding =
            ItemMangaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MangaViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        holder.bind(listManga[position])
    }

    override fun getItemCount(): Int = listManga.size
}
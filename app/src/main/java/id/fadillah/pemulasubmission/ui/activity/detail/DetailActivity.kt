package id.fadillah.pemulasubmission.ui.activity.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.fadillah.pemulasubmission.R
import id.fadillah.pemulasubmission.data.model.MangaEntity
import id.fadillah.pemulasubmission.databinding.ActivityDetailBinding
import id.fadillah.pemulasubmission.ui.adapter.MangaChapterAdapter
import id.fadillah.pemulasubmission.utils.ImageHelper
import id.fadillah.pemulasubmission.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_BUNDLE = "extra_bundle"
    }

    private lateinit var binding: ActivityDetailBinding
    private var fabFlag: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.getBundleExtra(EXTRA_BUNDLE) ?: Bundle.EMPTY
        val data = bundle.getParcelable(EXTRA_DATA) ?: MangaEntity("Unknown", " ", " ")
        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        val adapter = MangaChapterAdapter()

        startLayoutShimmer(true)
        with(binding.content.rvListChapter) {
            layoutManager = LinearLayoutManager(this@DetailActivity)
            this.adapter = adapter.apply {
                setHasStableIds(true)
            }
            setItemViewCacheSize(20)
        }
        binding.collapsingToolbar.title = data.title
        ImageHelper.getImage(binding.ivDetail, data.thumbnail)
        viewModel.getDetailManga(data.endpoint).observe(this, { manga ->
            adapter.setChapter(manga.listChapterEntity)
            adapter.notifyDataSetChanged()
            with(binding.content) {
                tvType.text = manga.type
                tvAuthor.text = manga.author
                tvStatus.text = manga.status
                tvGenreList.text = manga.genreList
                tvSynopsis.text = manga.synopsis
            }
            startLayoutShimmer(false)
        })

        binding.fabBookmark.setOnClickListener { view ->
            Snackbar.make(view, "Not implemented yet!", Snackbar.LENGTH_LONG)
                .setAction("Action") { TODO("Not yet implemented") }.show()
        }

        binding.fabContainer.setOnClickListener {
            if (fabFlag) {
                with(binding) {
                    fabBookmark.show()
                    fabShare.show()
                    fabShare.animate().translationY((-(fabBookmark.size + fabContainer.size)).toFloat())
                    fabBookmark.animate().translationY((-(fabContainer.size)).toFloat())
                    fabContainer.setImageResource(R.drawable.ic_baseline_clear)
                }
                fabFlag = false;
            }else {
                with(binding) {
                    fabShare.hide()
                    fabBookmark.hide()
                    fabShare.animate().translationY(0F)
                    fabBookmark.animate().translationY(0F)
                    fabContainer.setImageResource(R.drawable.ic_baseline_add)
                }
                fabFlag = true
            }
        }


    }

    private fun startLayoutShimmer(start: Boolean) {
        if (start) {
            binding.content.contentDetailShimmer.visibility = View.VISIBLE
            binding.content.contentDetailMain.visibility = View.GONE
        } else {
            binding.content.contentDetailShimmer.visibility = View.GONE
            binding.content.contentDetailMain.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
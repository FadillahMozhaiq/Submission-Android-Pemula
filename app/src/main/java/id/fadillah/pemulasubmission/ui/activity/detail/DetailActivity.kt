package id.fadillah.pemulasubmission.ui.activity.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import id.fadillah.pemulasubmission.R
import id.fadillah.pemulasubmission.data.model.MangaEntity
import id.fadillah.pemulasubmission.databinding.ActivityDetailBinding
import id.fadillah.pemulasubmission.ui.activity.imagedetail.ImageViewActivity
import id.fadillah.pemulasubmission.ui.activity.imagedetail.ImageViewActivity.Companion.EXTRA_DETAIL_IMAGE
import id.fadillah.pemulasubmission.ui.activity.imagedetail.ImageViewActivity.Companion.EXTRA_DETAIL_IMAGE_TITLE
import id.fadillah.pemulasubmission.ui.adapter.MangaChapterAdapter
import id.fadillah.pemulasubmission.utils.ImageHelper
import id.fadillah.pemulasubmission.viewmodel.ViewModelFactory
import kotlin.math.abs

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_BUNDLE = "extra_bundle"
    }

    private lateinit var binding: ActivityDetailBinding
    private var fabFlag: Boolean = true
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.fab_anim_in) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.fab_anim_out ) }

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

//        Inisialisasi View
        startLayoutShimmer(true)
        with(binding.content.rvListChapter) {
            layoutManager = LinearLayoutManager(this@DetailActivity)
            this.adapter = adapter.apply {
                setHasStableIds(true)
            }
            setHasFixedSize(true)
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

//        OnClickListener View
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
                    fabContainer.startAnimation(rotateOpen)
//                    fabContainer.setImageResource(R.drawable.ic_baseline_clear)
                }
                fabFlag = false;
            }else {
                with(binding) {
                    fabShare.hide()
                    fabBookmark.hide()
                    fabShare.animate().translationY(0F)
                    fabBookmark.animate().translationY(0F)
                    fabContainer.startAnimation(rotateClose)
                    fabContainer.setImageResource(R.drawable.ic_baseline_add)
                }
                fabFlag = true
            }
        }

        binding.ivDetail.setOnClickListener {
            val intent = Intent(this, ImageViewActivity::class.java).apply {
                putExtra(EXTRA_DETAIL_IMAGE, data.thumbnail)
                putExtra(EXTRA_DETAIL_IMAGE_TITLE, data.title)
            }
            startActivity(intent)
        }

        binding.appbarDetail.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                //  Collapsed
                binding.fabContainer.visibility = View.GONE
            }
            else {
                //Expanded
                binding.fabContainer.visibility = View.VISIBLE
            }
        })
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
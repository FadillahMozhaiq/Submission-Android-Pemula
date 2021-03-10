package id.fadillah.pemulasubmission.ui.activity.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ShareActionProvider
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
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
    private lateinit var viewModel: DetailViewModel
    private lateinit var data: MangaEntity
    private var fabFlag: Boolean = true
    private var fabBookmarkFlag: Boolean = false
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.fab_anim_in
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this,
            R.anim.fab_anim_out
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.getBundleExtra(EXTRA_BUNDLE) ?: Bundle.EMPTY
        data = bundle.getParcelable(EXTRA_DATA) ?: MangaEntity("Unknown", " ", " ")
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
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
        viewModel.isMangaBookmarked(data.endpoint).observe(this, {
            it?.let {
                fabBookmarkFlag = it > 0
                Log.d("HAPUS", fabBookmarkFlag.toString())
                if (fabBookmarkFlag)
                    binding.fabBookmark.setImageResource(R.drawable.ic_baseline_bookmark)
                else
                    binding.fabBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border)
            }
        })
        binding.collapsingToolbar.title = data.title
        ImageHelper.getImage(binding.ivDetail, data.thumbnail)
        viewModel.getDetailManga(data.endpoint).observe(this, { manga ->
            with(binding.content) {
                tvType.text = manga.type
                tvAuthor.text = manga.author
                tvStatus.text = manga.status
                tvGenreList.text = manga.genreList
                tvSynopsis.text = manga.synopsis
            }
            startLayoutShimmer(false)
            adapter.setChapter(manga.listChapterEntity)
            adapter.notifyDataSetChanged()
        })

//        OnClickListener View
        binding.fabBookmark.setOnClickListener { view ->
//            is bookmarked
            if (fabBookmarkFlag) {
                viewModel.removeBookmark(data)
                Snackbar.make(view, "Deleted from Bookmark!", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        viewModel.addToBookmark(data)
                        fabBookmarkFlag = true
                        binding.fabBookmark.setImageResource(R.drawable.ic_baseline_bookmark)
                    }.show()
                binding.fabBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border)
                fabBookmarkFlag = false
            } else {
                viewModel.addToBookmark(data)
                Snackbar.make(view, "Saved to Bookmarked!", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        viewModel.removeBookmark(data)
                        fabBookmarkFlag = false
                        binding.fabBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border)
                    }.show()
                binding.fabBookmark.setImageResource(R.drawable.ic_baseline_bookmark)
                fabBookmarkFlag = true
            }
        }

        binding.fabShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT, "Baca manga ${this@DetailActivity.data.title} sekarang!"
                )
            }
            startActivity(Intent.createChooser(intent, "Bagikan Manga Ini Sekarang!"))
        }

        binding.fabContainer.setOnClickListener {
            if (fabFlag) {
                with(binding) {
                    fabBookmark.show()
                    fabShare.show()
                    fabShare.animate()
                        .translationY((-(fabBookmark.size + fabContainer.size)).toFloat())
                    fabBookmark.animate().translationY((-(fabContainer.size)).toFloat())
                    fabContainer.startAnimation(rotateOpen)
                }
                fabFlag = false
            } else {
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
                putExtra(EXTRA_DETAIL_IMAGE, this@DetailActivity.data.thumbnail)
                putExtra(EXTRA_DETAIL_IMAGE_TITLE, this@DetailActivity.data.title)
            }
            startActivity(intent)
        }

        binding.appbarDetail.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                //  Collapsed
                binding.fabContainer.visibility = View.GONE
            } else {
                //Expanded
                binding.fabContainer.visibility = View.VISIBLE
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.isMangaBookmarked(data.endpoint).observe(this, {
            it?.let {
                fabBookmarkFlag = it > 0
                Log.d("HAPUS", fabBookmarkFlag.toString())
                if (fabBookmarkFlag)
                    binding.fabBookmark.setImageResource(R.drawable.ic_baseline_bookmark)
                else
                    binding.fabBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border)
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
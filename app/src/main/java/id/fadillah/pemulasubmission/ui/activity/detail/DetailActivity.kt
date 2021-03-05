package id.fadillah.pemulasubmission.ui.activity.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import id.fadillah.pemulasubmission.databinding.ActivityDetailBinding
import id.fadillah.pemulasubmission.utils.ImageHelper
import id.fadillah.pemulasubmission.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ENDPOINT = "extra_endpoint"
        const val EXTRA_THUMBNAIL = "extra_thumbnail"
    }

    private lateinit var endpoint: String
    private lateinit var thumbnailUrl: String
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        endpoint = intent.getStringExtra(EXTRA_ENDPOINT) ?: ""
        thumbnailUrl = intent.getStringExtra(EXTRA_THUMBNAIL) ?: ""
        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        ImageHelper.getImage(binding.ivDetail, thumbnailUrl)
        viewModel.getDetailManga(endpoint).observe(this, { manga ->
            binding.collapsingToolbar.title = manga.title
            with(binding.content) {
                tvType.text = manga.type
                tvAuthor.text = manga.author
                tvStatus.text = manga.status
                tvGenreList.text = manga.genreList
                tvSynopsis.text = manga.synopsis
            }
        })

        binding.fabBookmark.setOnClickListener { view ->
            Snackbar.make(view, "Not implemented yet!", Snackbar.LENGTH_LONG)
                .setAction("Action", object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        TODO("Not yet implemented")
                    }
                }).show()
        }
    }
}
package id.fadillah.pemulasubmission.ui.activity.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import id.fadillah.pemulasubmission.databinding.ActivityDetailBinding
import id.fadillah.pemulasubmission.utils.ImageHelper
import id.fadillah.pemulasubmission.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ENDPOINT = "extra_endpoint"
    }

    private lateinit var endpoint: String
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        endpoint = intent.getStringExtra(EXTRA_ENDPOINT) ?: ""
        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        viewModel.getDetailManga(endpoint).observe(this, { manga ->
            binding.collapsingToolbar.title = manga.title
            ImageHelper.getImage(binding.ivDetail, manga.thumbnail)
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
                .setAction("Action", null).show()
        }
    }
}
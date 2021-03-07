package id.fadillah.pemulasubmission.ui.activity.chapterview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.fadillah.pemulasubmission.R
import id.fadillah.pemulasubmission.databinding.ActivityChapterViewBinding
import id.fadillah.pemulasubmission.ui.adapter.ChapterViewAdapter
import id.fadillah.pemulasubmission.viewmodel.ViewModelFactory

class ChapterViewActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_CHAPTER_URL = "extra_chapter_url"
    }

    private lateinit var binding: ActivityChapterViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChapterViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chapterAdapter = ChapterViewAdapter()
        val endpoint = intent.getStringExtra(EXTRA_CHAPTER_URL) ?: " "
        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[ChapterViewModel::class.java]

        with(binding.rvChapterViewer) {
            layoutManager = LinearLayoutManager(this@ChapterViewActivity)
            adapter = chapterAdapter
            setHasFixedSize(true)
        }

        binding.pbChapterView.visibility = View.VISIBLE
        viewModel.getChapter(endpoint).observe(this, {
            chapterAdapter.setChapters(it)
            chapterAdapter.notifyDataSetChanged()
            binding.pbChapterView.visibility = View.GONE
        })
    }
}
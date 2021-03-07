package id.fadillah.pemulasubmission.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.fadillah.pemulasubmission.R
import id.fadillah.pemulasubmission.databinding.ActivityImageViewBinding
import id.fadillah.pemulasubmission.utils.ImageHelper

class ImageViewActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DETAIL_IMAGE_TITLE = "extra_detail_image_title"
        const val EXTRA_DETAIL_IMAGE = "extra_detail_image"
    }

    private lateinit var binding: ActivityImageViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.getStringExtra(EXTRA_DETAIL_IMAGE_TITLE) ?: "Image Profile"
        val imageUrl =
            intent.getStringExtra(EXTRA_DETAIL_IMAGE) ?: R.drawable.placeholder.toString()
        when(imageUrl) {
            "PosterAndroid" ->
                ImageHelper.getImage(binding.ivDetailView, R.drawable.poster_android)
            "PhotoProfile" ->
                ImageHelper.getImage(binding.ivDetailView, R.drawable.profile_photo)
            else ->
                ImageHelper.getImage(binding.ivDetailView, imageUrl)
        }
        supportActionBar?.title = title
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
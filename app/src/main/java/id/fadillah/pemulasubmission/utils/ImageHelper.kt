package id.fadillah.pemulasubmission.utils

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import id.fadillah.pemulasubmission.R
import java.lang.Exception

object ImageHelper {
    fun getImage(
        imageView: ImageView,
        url: String,
        placeholder: Int = R.drawable.ic_no_images,
        error: Int = R.drawable.ic_no_images
    ) {
        Picasso.get()
            .load(url)
            .placeholder(placeholder)
            .error(error)
            .into(imageView, object : Callback {
                override fun onSuccess() {}

                override fun onError(e: Exception?) {
                    Log.e("Picasso", "Error: ${e?.message}")
                    Log.e("Picasso", "URL: ${e?.message}")
                }
            })
    }
}
package id.fadillah.pemulasubmission.utils

import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import id.fadillah.pemulasubmission.R

object ImageHelper {
    private val TAG = ImageHelper::class.java.simpleName
    fun getImage(
        imageView: ImageView,
        url: String,
        placeholder: Int = R.drawable.placeholder,
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
//    fun getImage(
//        imageView: ImageView,
//        url: String,
//        placeholder: Int = R.drawable.placeholder,
//        error: Int = R.drawable.ic_no_images
//    ) {
//        Glide
//            .with(imageView.context)
//            .load(url)
//            .listener(object : RequestListener<Drawable> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    Log.e(TAG, "Error Load Image: ${e?.message}")
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    Log.d(TAG, "Load Image Success")
//                    return false
//                }
//
//            })
//            .placeholder(placeholder)
//            .error(error)
//            .into(imageView)
//    }
}
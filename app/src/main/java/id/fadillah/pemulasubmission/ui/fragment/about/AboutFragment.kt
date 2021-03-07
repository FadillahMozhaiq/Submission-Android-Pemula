package id.fadillah.pemulasubmission.ui.fragment.about

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import id.fadillah.pemulasubmission.R
import id.fadillah.pemulasubmission.databinding.FragmentAboutBinding
import id.fadillah.pemulasubmission.ui.activity.imagedetail.ImageViewActivity
import id.fadillah.pemulasubmission.ui.activity.imagedetail.ImageViewActivity.Companion.EXTRA_DETAIL_IMAGE
import id.fadillah.pemulasubmission.ui.activity.imagedetail.ImageViewActivity.Companion.EXTRA_DETAIL_IMAGE_TITLE
import id.fadillah.pemulasubmission.utils.UrlHelper.URL_LinkedIn

class AboutFragment : Fragment() {
    companion object {
        private const val LinkedInPackage = "com.linkedin.android"

        @JvmStatic
        fun newInstance() =
            AboutFragment()
    }

    private var binding: FragmentAboutBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnAddFriend?.setOnClickListener {
            if (isAppInstalled(requireContext(), LinkedInPackage)) {
                try {
                    val linkedinIntent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse(URL_LinkedIn)
                        setPackage(LinkedInPackage)
                    }
                    startActivity(linkedinIntent)
                } catch (e: Exception) {
                    Snackbar.make(
                        binding!!.root,
                        "Can't open LinkedIn\nError: ${e.message}",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Okay") {  }
                        .setBackgroundTint(resources.getColor(R.color.red_danger))
                        .show()
                }
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_LinkedIn))
                startActivity(intent)
            }
        }
        binding?.ivPoster?.setOnClickListener {
            val intent = Intent(context, ImageViewActivity::class.java).apply {
                putExtra(EXTRA_DETAIL_IMAGE, "PosterAndroid")
                putExtra(EXTRA_DETAIL_IMAGE_TITLE, "Poster Android")
            }
            startActivity(intent)
        }
        binding?.ivProfilePhoto?.setOnClickListener {
            val intent = Intent(context, ImageViewActivity::class.java).apply {
                putExtra(EXTRA_DETAIL_IMAGE, "PhotoProfile")
                putExtra(EXTRA_DETAIL_IMAGE_TITLE, "Photo Profile")
            }
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun isAppInstalled(context: Context, packageName: String): Boolean = try {
        context.packageManager.getApplicationInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}
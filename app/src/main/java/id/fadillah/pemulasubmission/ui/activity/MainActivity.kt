package id.fadillah.pemulasubmission.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.fxn.OnBubbleClickListener
import id.fadillah.pemulasubmission.R
import id.fadillah.pemulasubmission.databinding.ActivityMainBinding
import id.fadillah.pemulasubmission.ui.adapter.SectionsPagerAdapter
import id.fadillah.pemulasubmission.utils.ImageHelper

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pageradapter: SectionsPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        pageradapter = SectionsPagerAdapter(this)
        setContentView(binding.root)

        ImageHelper.getImage(
            binding.ivBackground,
            "https://i.pinimg.com/originals/0b/44/77/0b4477823d40da7c271d1afae34203e9.jpg",
            error = R.drawable.background
        )

        binding.vpMain.apply {
            adapter = pageradapter
        }
        binding.tabs.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when (id) {
                    R.id.home_menu -> binding.vpMain.setCurrentItem(0, false)
                    R.id.favorite_menu -> binding.vpMain.setCurrentItem(1, false)
                    R.id.about_menu -> binding.vpMain.setCurrentItem(2, false)
                }
            }
        })
        binding.vpMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabs.setSelected(position)
            }
        })


    }
}
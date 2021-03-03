package id.fadillah.pemulasubmission.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.fadillah.pemulasubmission.ui.fragment.about.AboutFragment
import id.fadillah.pemulasubmission.ui.fragment.favorite.FavoriteFragment
import id.fadillah.pemulasubmission.ui.fragment.home.HomeFragment

class SectionsPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> HomeFragment()
        1 -> FavoriteFragment()
        2 -> AboutFragment()
        else -> Fragment()
    }
}
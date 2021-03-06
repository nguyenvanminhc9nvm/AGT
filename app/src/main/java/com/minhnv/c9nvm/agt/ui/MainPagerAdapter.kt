package com.minhnv.c9nvm.agt.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.minhnv.c9nvm.agt.ui.comic.ComicFragment
import com.minhnv.c9nvm.agt.ui.humor.HumorFragment
import com.minhnv.c9nvm.agt.ui.sport.SportFragment

class MainPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HumorFragment()
            1 -> ComicFragment()
            else -> SportFragment()
        }
    }
}
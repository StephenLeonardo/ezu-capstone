package com.dicoding.salsahava.seeara.ui.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.salsahava.seeara.R
import com.dicoding.salsahava.seeara.ui.history.HistoryFragment
import com.dicoding.salsahava.seeara.ui.recorder.RecorderFragment

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.record_fragment,
            R.string.history_fragment
        )
    }

    override fun getCount(): Int = TAB_TITLE.size

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> RecorderFragment()
            1 -> HistoryFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence =
        context.resources.getString(TAB_TITLE[position])
}
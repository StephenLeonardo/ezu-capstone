package com.dicoding.salsahava.seeara.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.salsahava.seeara.R
import com.dicoding.salsahava.seeara.history.HistoryFragment
import com.dicoding.salsahava.seeara.recorder.RecorderFragment

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm){

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
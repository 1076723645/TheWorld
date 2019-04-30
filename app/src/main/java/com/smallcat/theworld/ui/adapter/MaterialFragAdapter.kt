package com.smallcat.theworld.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.smallcat.theworld.ui.fragment.OtherFragment

/**
 * @author smallCut
 * @date 2018/9/14
 */
class MaterialFragAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf("材料", "藏品", "其他")

    override fun getItem(position: Int): Fragment {
        return OtherFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}
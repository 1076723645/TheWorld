package com.smallcat.theworld.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.smallcat.theworld.ui.fragment.BossDetailFragment
import java.util.ArrayList

/**
 * @author smallCut
 * @date 2018/9/13
 */
class BossPageAdapter(fm: FragmentManager, data: List<String>) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf("Boss掉落", "Boss技能", "参考打法")
    private var data = ArrayList<String>()

    init {
        this.data = data as ArrayList<String>
    }

    override fun getItem(position: Int): Fragment {
        return BossDetailFragment.newInstance(data[position])
    }

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}

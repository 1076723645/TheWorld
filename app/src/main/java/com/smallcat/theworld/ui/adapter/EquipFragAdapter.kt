package com.smallcat.theworld.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.smallcat.theworld.ui.fragment.EquipListFragment
import com.smallcat.theworld.ui.fragment.MyEquipListFragment

/**
 * @author smallCut
 * @date 2018/9/11
 */
class EquipFragAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf("我的", "武器", "头盔", "衣服", "饰品", "翅膀")

    override fun getItem(position: Int): Fragment {
        if (position == 0){
            return MyEquipListFragment()
        }
        return EquipListFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}
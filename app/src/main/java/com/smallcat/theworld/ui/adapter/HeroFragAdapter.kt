package com.smallcat.theworld.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.smallcat.theworld.ui.fragment.*

/**
 * @author smallCut
 * @date 2018/9/11
 */
class HeroFragAdapter(fm: FragmentManager, val name:String) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf("技能", "介绍", "专属", "玩法", "攻略")

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return HeroSkillFragment.newInstance(name)
            1 -> return HeroIntroduceFragment.newInstance(name)
            2 -> return HeroFragment.newInstance(name)
            3 -> return RecommendFragment()
            4 -> return HeroStrategyFragment.newInstance(name)
        }
        return HeroSkillFragment.newInstance(name)
    }

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}
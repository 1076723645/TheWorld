package com.smallcat.theworld.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.smallcat.theworld.ui.fragment.HeroFragment
import com.smallcat.theworld.ui.fragment.HeroIntroduceFragment
import com.smallcat.theworld.ui.fragment.HeroSkillFragment
import com.smallcat.theworld.ui.fragment.RecommendFragment

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
            4 -> return RecommendFragment()
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
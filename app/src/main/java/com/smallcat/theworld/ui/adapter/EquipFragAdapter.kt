package com.smallcat.theworld.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.smallcat.theworld.ui.fragment.EquipListFragment
import com.smallcat.theworld.ui.fragment.RecordFragment
import com.smallcat.theworld.utils.toast

/**
 * @author smallCut
 * @date 2018/9/11
 */
class EquipFragAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        if (position == 0){
            return RecordFragment()
        }
        return EquipListFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return 6
    }

    override fun getItemPosition(any: Any): Int {
        if (any is EquipListFragment){
            "销毁".toast()
            return super.getItemPosition(any)
        }
        return POSITION_NONE
    }
}
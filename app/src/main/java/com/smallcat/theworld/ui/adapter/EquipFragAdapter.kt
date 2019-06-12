package com.smallcat.theworld.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.smallcat.theworld.ui.fragment.EquipListFragment
import com.smallcat.theworld.ui.fragment.RecordFragment

/**
 * @author smallCut
 * @date 2018/9/11
 */
class EquipFragAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0){
            return RecordFragment()
        }
        return EquipListFragment.newInstance(position)
    }

}
package com.smallcat.theworld.ui.fragment


import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseFragment
import com.smallcat.theworld.ui.adapter.EquipFragAdapter

class EquipFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_equip

    override fun initData() {
        val viewPager = mView.findViewById<ViewPager>(R.id.vp_equip)
        val tab = mView.findViewById<TabLayout>(R.id.tab_equip)
        val adapter = EquipFragAdapter(activity!!.supportFragmentManager, mContext)
        viewPager.adapter = adapter
        tab.setupWithViewPager(viewPager)
    }
}

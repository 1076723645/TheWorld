package com.smallcat.theworld.ui.fragment


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smallcat.theworld.R
import com.smallcat.theworld.ui.adapter.EquipFragAdapter
import me.yokeyword.fragmentation.SupportFragment


/**
 * @author Administrator
 */
class EquipFragment : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_equip, container, false)
        initView(view)
        return view
    }

    private fun initView(v: View) {
        val viewPager = v.findViewById<ViewPager>(R.id.vp_equip)
        val tab = v.findViewById<TabLayout>(R.id.tab_equip)
        val adapter = EquipFragAdapter(activity!!.supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        tab.setupWithViewPager(viewPager)
    }
}


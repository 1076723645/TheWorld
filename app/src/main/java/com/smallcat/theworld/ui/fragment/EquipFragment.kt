package com.smallcat.theworld.ui.fragment


import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseFragment
import com.smallcat.theworld.ui.adapter.EquipFragAdapter
import kotlinx.android.synthetic.main.fragment_equip.*


class EquipFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_equip

    override fun initData() {
        val adapter = EquipFragAdapter(activity!!.supportFragmentManager, mContext)
        vp_equip.adapter = adapter
        tab_equip.setupWithViewPager(vp_equip)
    }

}

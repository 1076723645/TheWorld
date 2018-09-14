package com.smallcat.theworld.ui.fragment


import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseFragment
import com.smallcat.theworld.ui.adapter.MaterialFragAdapter
import com.smallcat.theworld.utils.DataUtil
import kotlinx.android.synthetic.main.fragment_material.*

class MaterialFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_material

    override fun initData() {
        val adapter = MaterialFragAdapter(activity!!.supportFragmentManager)
        tab_material.setupWithViewPager(vp_material)
        DataUtil.reflex(tab_material)
        vp_material.offscreenPageLimit = 2
        vp_material.adapter = adapter
    }
}

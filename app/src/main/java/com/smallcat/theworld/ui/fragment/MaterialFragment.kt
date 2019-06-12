package com.smallcat.theworld.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.smallcat.theworld.R
import com.smallcat.theworld.ui.adapter.MaterialFragAdapter
import me.yokeyword.fragmentation.SupportFragment

/**
 * @author Administrator
 */
class MaterialFragment : SupportFragment() {

    private var v: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_material, container, false)
        return v
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val viewPager = v!!.findViewById<ViewPager>(R.id.vp_material)
        val tab = v!!.findViewById<TabLayout>(R.id.tab_material)
        val adapter = MaterialFragAdapter(activity!!.supportFragmentManager)
        tab.setupWithViewPager(viewPager)
        //DataUtil.reflex(tab)
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = adapter
    }

}

package com.smallcat.theworld.ui.fragment


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smallcat.theworld.R
import com.smallcat.theworld.ui.adapter.MaterialFragAdapter
import com.smallcat.theworld.utils.DataUtil
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
        DataUtil.reflex(tab)
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = adapter

    }

    override fun onSaveInstanceState(outState: Bundle) {
        /*super.onSaveInstanceState(outState);
         防止Activity切换到后台之后，由于内存不够，Activity被系统回收，但附属在上面的fragment被保存了。
         在切换到前台的时候，activity被重新实例化，而fragment再getActivity()时，get的是之前被回收掉的，
         而不是重新实例化的activity，整个APP会卡住*/
    }
}

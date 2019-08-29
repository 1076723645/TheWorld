package com.smallcat.theworld.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.MyRecord
import com.smallcat.theworld.ui.adapter.EquipFragAdapter
import me.yokeyword.fragmentation.SupportFragment
import org.litepal.crud.DataSupport


/**
 * @author Administrator
 */
class EquipFragment : SupportFragment() {

    private val tabTitles = arrayOf("我的", "武器", "头盔", "衣服", "饰品", "翅膀")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_equip, container, false)
        initView(view)
        return view
    }

    private fun initView(v: View) {
        val viewPager = v.findViewById<ViewPager>(R.id.vp_equip)
        val mTabLayout = v.findViewById<TabLayout>(R.id.tab_equip)
        val adapter = EquipFragAdapter(childFragmentManager)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        mTabLayout.removeAllTabs()
        for (i in tabTitles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(i))
        }
        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPager.setCurrentItem(p0!!.position, false)
            }

        })
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                mTabLayout.setScrollPosition(position, positionOffset, false)
            }

            override fun onPageSelected(position: Int) {
                mTabLayout.setScrollPosition(position, 0F, true)
            }

        })
        val data = DataSupport.findAll(MyRecord::class.java)
        if (data.isEmpty()) {
            viewPager.setCurrentItem(1, false)
        }
    }

}


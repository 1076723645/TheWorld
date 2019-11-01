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
        mTabLayout.setupWithViewPager(viewPager, false)
        val data = DataSupport.findAll(MyRecord::class.java)
        if (data.isEmpty()) {
            viewPager.setCurrentItem(1, false)
        }
    }

}


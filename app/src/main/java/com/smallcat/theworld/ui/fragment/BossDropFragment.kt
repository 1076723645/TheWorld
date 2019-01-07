package com.smallcat.theworld.ui.fragment


import android.os.Bundle

import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseFragment
import com.smallcat.theworld.ui.adapter.AccessAdapter
import com.smallcat.theworld.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_boss_drop.*

private const val ARG_PARAM1 = "param1"

class BossDropFragment : BaseFragment() {

    private var list = ArrayList<String>()

    override val layoutId: Int = R.layout.fragment_boss_drop

    /**
     * 懒加载
     */
    override fun lazyLoading() {
        val s = arguments?.getString(ARG_PARAM1)
        list = AppUtils.stringToList(s) as ArrayList<String>
        val adapter = AccessAdapter(list)
        adapter.setOnItemClickListener { _, _, position ->
            val name = list[position].substring(0, list[position].indexOf("—"))
            AppUtils.goEquipDetailActivity(mContext, name)
        }
        rv_list.isNestedScrollingEnabled = false
        rv_list.adapter = adapter
    }

    companion object {
        fun newInstance(data: String): BossDropFragment {
            val bundle = Bundle()
            bundle.putString(ARG_PARAM1, data)
            val fragment = BossDropFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}

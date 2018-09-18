package com.smallcat.theworld.ui.fragment


import android.os.Bundle
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_boss_detail.*

class BossDetailFragment : BaseFragment() {

    companion object {
        fun newInstance(data: String): BossDetailFragment {
            val bundle = Bundle()
            bundle.putString("index", data)
            val weatherFragment = BossDetailFragment()
            weatherFragment.arguments = bundle
            return weatherFragment
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_boss_detail

    override fun lazyLoading() {
        val data = arguments?.getString("index")
        tv_skill.text = data
    }

}

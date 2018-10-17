package com.smallcat.theworld.ui.fragment

import android.os.Bundle
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxFragment


class RecommendFragment : RxFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_recommand

    override fun initView() {
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                RecommendFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}

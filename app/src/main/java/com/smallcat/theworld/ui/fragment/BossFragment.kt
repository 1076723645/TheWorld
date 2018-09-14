package com.smallcat.theworld.ui.fragment


import android.content.Intent
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseFragment
import com.smallcat.theworld.ui.activity.BossDetailActivity
import com.smallcat.theworld.ui.adapter.BossAdapter
import com.smallcat.theworld.utils.DataUtil
import kotlinx.android.synthetic.main.fragment_boss.*


class BossFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_boss

    override fun initData() {
        val list = DataUtil.getBossList()
        val adapter = BossAdapter(list)
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(mContext, BossDetailActivity::class.java)
            intent.putExtra("id", list[position].id.toString())
            startActivity(intent)
        }
        rv_boss.adapter = adapter
    }

}

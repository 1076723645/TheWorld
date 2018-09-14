package com.smallcat.theworld.ui.activity

import android.view.View
import butterknife.OnClick
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseActivity
import com.smallcat.theworld.model.db.Exclusive
import com.smallcat.theworld.ui.adapter.ExclusiveItemAdapter
import kotlinx.android.synthetic.main.activity_career_introduce.*
import kotlinx.android.synthetic.main.normal_toolbar.*
import org.litepal.crud.DataSupport

class CareerIntroduceActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_career_introduce

    override fun initData() {
        val name = intent.getStringExtra("name")
        tv_title.text = name
        val exclusives = DataSupport.where("profession = ?", name).find(Exclusive::class.java)
        val adapter = ExclusiveItemAdapter(exclusives)
        adapter.setOnItemClickListener { adapter, view, position ->

        }
        rv_list.isFocusable = false
        rv_list.isNestedScrollingEnabled = false
        rv_list.adapter = adapter
    }

    @OnClick(R.id.iv_back, R.id.ll_way, R.id.ll_advanced_way)
    fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> onBackPressed()
            R.id.ll_way -> finish()
            R.id.ll_advanced_way -> finish()
        }
    }
}

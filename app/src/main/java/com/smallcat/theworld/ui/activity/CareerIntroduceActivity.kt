package com.smallcat.theworld.ui.activity

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseActivity
import com.smallcat.theworld.model.db.Exclusive
import com.smallcat.theworld.ui.adapter.ExclusiveItemAdapter
import org.litepal.crud.DataSupport

class CareerIntroduceActivity : BaseActivity() {

    @BindView(R.id.tv_title)
    lateinit var tvTitle: TextView
    @BindView(R.id.rv_list)
    lateinit var recyclerView: RecyclerView

    override val layoutId: Int
        get() = R.layout.activity_career_introduce

    override fun initData() {
        val name = intent.getStringExtra("name")
        tvTitle.text = name
        val exclusives = DataSupport.where("profession = ?", name).find(Exclusive::class.java)
        val adapter = ExclusiveItemAdapter(exclusives)
        adapter.setOnItemClickListener { adapter, view, position ->

        }
        recyclerView.isFocusable = false
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.adapter = adapter
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

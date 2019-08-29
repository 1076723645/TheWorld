package com.smallcat.theworld.ui.activity

import android.content.Context
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.view.MenuItem
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxActivity
import com.smallcat.theworld.model.bean.ImgData
import com.smallcat.theworld.ui.adapter.HeroFragAdapter
import com.smallcat.theworld.utils.fitSystemAllScroll
import kotlinx.android.synthetic.main.activity_career_detail.*

class CareerDetailActivity : RxActivity() {

    private var bean = ImgData()

    override val layoutId: Int
        get() = R.layout.activity_career_detail

    override fun fitSystem() {
        fitSystemAllScroll(this)
    }

    override fun initData() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        intent.getSerializableExtra("data")?.let {
            bean = it as ImgData
        }
        val mPosition = intent.getIntExtra("position", 0)
        collapsing.title = bean.name
        iv_bg.setImageResource(bean.imgUrl)
        val adapter = HeroFragAdapter(supportFragmentManager, bean.name)
        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = 3
        tab.setupWithViewPager(view_pager)
        view_pager.currentItem = mPosition
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

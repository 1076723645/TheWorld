package com.smallcat.theworld.ui.activity

import android.view.MenuItem
import com.bumptech.glide.Glide
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxActivity
import com.smallcat.theworld.ui.adapter.HeroFragAdapter
import com.smallcat.theworld.utils.fitSystemAllScroll
import kotlinx.android.synthetic.main.activity_career_detail.*

class CareerDetailActivity : RxActivity() {

    override val layoutId: Int
        get() = R.layout.activity_career_detail

    override fun fitSystem() {
        fitSystemAllScroll(this)
    }

    override fun initData() {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val title = intent.getStringExtra("name")
        val mPosition = intent.getIntExtra("position",0)
        val imgId = intent.getIntExtra("imgId",0)
        collapsing.title = title
        iv_bg.setImageResource(imgId)
        val adapter = HeroFragAdapter(supportFragmentManager, title)
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

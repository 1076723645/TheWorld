package com.smallcat.theworld.ui.activity

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
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
        bean = intent.getSerializableExtra("data") as ImgData
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

    private fun loadImg() {
        //存在记录的高度时先Layout再异步加载图片
        if (bean.height > 0) {
            val layoutParams = iv_bg.layoutParams
            layoutParams.height = bean.height
        }
        //获取屏幕宽度
        val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        val display = windowManager.defaultDisplay
        display.getMetrics(dm)
        val screenWidth = dm.widthPixels

        val simpleTarget = object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                if (bean.height <= 0) {
                    val width = resource.width
                    val height = resource.height
                    val realHeight = screenWidth * height / width / 2
                    bean.height = realHeight
                    val lp = iv_bg.layoutParams
                    lp.height = realHeight
                    if (width < screenWidth / 2)
                        lp.width = screenWidth / 2
                }
                iv_bg.setImageBitmap(resource)
            }
        }
        Glide.with(mContext).asBitmap().load(bean.imgUrl).into(simpleTarget)
    }
}

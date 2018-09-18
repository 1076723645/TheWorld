package com.smallcat.theworld.ui.activity

import android.widget.ImageView
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseActivity
import com.smallcat.theworld.utils.SystemFit

class ImageShowActivity : BaseActivity() {

    override val layoutId: Int
        get() = R.layout.activity_image_show

    override fun fitSystem() {
        SystemFit.fitSystem(this)
    }

    override fun initData() {
        val img = intent.getIntExtra("img", 0)
        val imageView = findViewById<ImageView>(R.id.iv_show)
        imageView.setImageResource(img)
        imageView.setOnClickListener { onBackPressed() }
    }

}

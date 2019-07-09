package com.smallcat.theworld.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.smallcat.theworld.App
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseActivity
import com.smallcat.theworld.utils.AppUtils
import com.smallcat.theworld.utils.getResourceColor
import com.smallcat.theworld.utils.sharedPref
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.normal_toolbar.*

class SettingActivity : BaseActivity() {

    private var isChangeTheme: Boolean = false
    private var isBlack: Boolean = false
    private var isChangeOrder = false
    private var noticeDialog: Dialog? = null

    override val layoutId: Int
        get() = R.layout.activity_setting

    override fun initData() {
        tv_title.setText(R.string.app_name)
        tv_version.text = AppUtils.getVerName(mContext)
        isBlack = sharedPref.isBlack
        sb_night.isChecked = isBlack
        sb_tip.isChecked = sharedPref.isShow
        sb_night.setOnCheckedChangeListener { _, _ ->  changeTheme() }
        sb_tip.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.isShow = isChecked
            if (isChecked) {
                showDialog()
            }
        }
        /*val path = sharedPref.splashPath
        if (path != "") {
            Glide.with(this).load(path).into(iv_splash)
        }
        ll_splash.setOnClickListener { DataUtil.addPicture(this, 1, PictureConfig.CHOOSE_REQUEST) }*/
        sb_back.isChecked = sharedPref.isBack
        sb_back.setOnCheckedChangeListener{ _, isChecked ->
            sharedPref.isBack = isChecked
            isChangeOrder = true
        }
        tv_times.text = "${sharedPref.times}"
        iv_back.setOnClickListener {
            if (isChangeTheme){
                App.getInstance().finishAllActivityExcept(this)
                startActivity(MainActivity::class.java)
            }
            if (isChangeOrder) {
                App.getInstance().finishAllActivityExcept(this)
                startActivity(MainActivity::class.java)
            }
            finish()
        }
    }

    private fun changeTheme() {
        isChangeTheme = true
        showAnimation()
        toggleThemeSetting()
        refreshUI()
    }

    private fun toggleThemeSetting() {
        if (isBlack) {
            setTheme(R.style.AppTheme)
            sharedPref.isBlack = false
        } else {
            setTheme(R.style.BlackTheme)
            sharedPref.isBlack = true
        }
        isBlack = !isBlack
    }

    private fun refreshUI() {
        val background = TypedValue()//背景色
        val theme = theme
        theme.resolveAttribute(R.attr.colorPrimary, background, true)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setBackgroundResource(background.resourceId)
        refreshStatusBar()
    }

    private fun refreshStatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            val typedValue = TypedValue()
            val theme = theme
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
            window.statusBarColor = getResourceColor(typedValue.resourceId)
        }
    }

    private fun showAnimation() {
        val decorView = window.decorView
        val cacheBitmap = getCacheBitmapFromView(decorView)
        if (decorView is ViewGroup && cacheBitmap != null) {
            val view = View(this)
            view.background = BitmapDrawable(resources, cacheBitmap)
            val layoutParam = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            decorView.addView(view, layoutParam)
            val objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
            objectAnimator.duration = 300
            objectAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    decorView.removeView(view)
                }
            })
            objectAnimator.start()
        }
    }

    private fun showDialog() {
        if (noticeDialog == null) {
            noticeDialog = Dialog(this, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_notice, null)
        noticeDialog?.setContentView(view)
        noticeDialog?.setCanceledOnTouchOutside(false)
        noticeDialog?.setCancelable(true)
        val btnReal = view.findViewById<Button>(R.id.btn_go)
        val btnIndex = view.findViewById<Button>(R.id.btn_cancel)
        noticeDialog?.show()
        btnReal.setOnClickListener {
            sb_tip.isChecked = false
            noticeDialog?.dismiss()
        }
        btnIndex.setOnClickListener { noticeDialog?.dismiss() }
    }

    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
    private fun getCacheBitmapFromView(view: View): Bitmap? {
        val drawingCacheEnabled = true
        view.isDrawingCacheEnabled = drawingCacheEnabled
        view.buildDrawingCache(drawingCacheEnabled)
        val drawingCache = view.drawingCache
        val bitmap: Bitmap?
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache)
            view.isDrawingCacheEnabled = false
        } else {
            bitmap = null
        }
        return bitmap
    }

    override fun onBackPressedSupport() {
        if (isChangeTheme || isChangeOrder) {
            App.getInstance().finishAllActivityExcept(this)
            startActivity(MainActivity::class.java)
        }
        finish()
    }

}

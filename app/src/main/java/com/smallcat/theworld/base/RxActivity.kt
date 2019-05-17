package com.smallcat.theworld.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import butterknife.ButterKnife
import butterknife.Unbinder
import com.smallcat.theworld.App
import com.smallcat.theworld.R
import com.smallcat.theworld.ui.activity.SplashActivity
import com.smallcat.theworld.utils.AppStatusManager
import com.smallcat.theworld.utils.LogUtil
import com.smallcat.theworld.utils.adaptScreen4VerticalSlide
import com.smallcat.theworld.utils.sharedPref
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.normal_toolbar.*
import me.yokeyword.fragmentation.SupportActivity

/**
 * Created by smallCut on 2018/4/27.
 */
abstract class RxActivity : SupportActivity() {

    protected lateinit var mContext: Context
    private var loadingDialog: Dialog? = null
    private var mCompositeDisposable: CompositeDisposable? = null

    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        checkAppStatus()
        super.onCreate(savedInstanceState)
        initTheme()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        fitSystem()
        adaptScreen4VerticalSlide(this, 360)
        setContentView(layoutId)
        mContext = this
        iv_back?.setOnClickListener { finish() }
        initData()
    }

    private fun checkAppStatus() {
        if (AppStatusManager.getInstance().appStatus == AppStatusManager.AppStatusConstant.APP_FORCE_KILLED) {
            LogUtil.e("activity被回收")
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }else{
            LogUtil.e("正常启动")
        }
    }

    override fun onDestroy() {
        dismissLoading()
        unSubscribe()
        super.onDestroy()
    }

    protected open fun fitSystem() {}

    protected open fun initTheme() {
        if (!sharedPref.isBlack){
            setTheme(R.style.AppTheme)
        }else{
            setTheme(R.style.BlackTheme)
        }
    }

    protected abstract fun initData()

    protected fun startActivityFinish(cls: Class<*>) {
        startActivity(getIntent(cls))
        finish()
    }

    protected fun startActivity(cls: Class<*>) {
        startActivity(getIntent(cls))
    }

    protected fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = Dialog(mContext, R.style.CustomDialog)
        }
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null)
        loadingDialog!!.setContentView(view)
        loadingDialog!!.setCanceledOnTouchOutside(true)
        loadingDialog!!.setCancelable(true)
        loadingDialog!!.show()
    }

    protected fun dismissLoading() {
        if (loadingDialog != null && loadingDialog!!.isShowing)
            loadingDialog!!.dismiss()
    }

    private fun unSubscribe() {
        if (mCompositeDisposable != null)
            mCompositeDisposable!!.clear()
    }

    protected fun addSubscribe(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)
    }

    protected fun getIntent(cls: Class<*>): Intent {
        return Intent(mContext, cls)
    }

}

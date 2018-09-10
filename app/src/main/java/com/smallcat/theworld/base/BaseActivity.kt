package com.smallcat.theworld.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import butterknife.ButterKnife
import butterknife.Unbinder
import com.smallcat.theworld.R
import me.yokeyword.fragmentation.SupportActivity

/**
 * Created by smallCut on 2018/4/27.
 */
abstract class BaseActivity : SupportActivity() {

    protected lateinit var mContext: Context
    private var mUnbind: Unbinder? = null
    private var loadingDialog: Dialog? = null

    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        fitSystem()
        setContentView(layoutId)
        mUnbind = ButterKnife.bind(this)
        mContext = this
        initData()
    }

    override fun onDestroy() {
        dismissLoading()
        mUnbind!!.unbind()
        super.onDestroy()
    }

    protected open fun fitSystem() {
    }

    protected abstract fun initData()

    protected fun startActivityFinish(cls: Class<*>) {
        startActivity(getIntent(cls))
        finish()
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

    protected fun getIntent(cls: Class<*>): Intent {
        return Intent(mContext, cls)
    }

}

package com.smallcat.theworld.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.pgyersdk.update.PgyUpdateManager
import com.pgyersdk.update.UpdateManagerListener
import com.pgyersdk.update.javabean.AppBean
import com.smallcat.theworld.App
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseActivity
import com.smallcat.theworld.ui.fragment.*
import com.smallcat.theworld.utils.*
import com.tbruyelle.rxpermissions2.RxPermissions
import me.yokeyword.fragmentation.ISupportFragment

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var fg1: EquipFragment
    private lateinit var fg2: ExclusiveFragment
    private lateinit var fg3: BossFragment
    private lateinit var fg4: MaterialFragment

    companion object {
        private const val EQUIP = 1
        private const val EXCLUSIVE = 2
        private const val BOSS = 3
        private const val MATERIAL = 4
    }

    private var show = EQUIP
    private var hide = EQUIP
    private var lastBackTime = 0L

    private var dialog: Dialog? = null
    private var noticeDialog: Dialog? = null

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun fitSystem() {
        fitSystemAllScroll(this)
    }

    override fun initData() {
        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        navView.setCheckedItem(R.id.nav_equip)

        /*val path = sharedPref.splashPath
        if (path != "") {
            val headView = navView.getHeaderView(0)
            val ivBg = headView.findViewById<ImageView>(R.id.iv_bg)
            ivBg.setImageResource(R.drawable.zy_ss)
        }*/

        val ivHead = findViewById<ImageView>(R.id.iv_logo)
        ivHead.setOnClickListener { mDrawerLayout.openDrawer(GravityCompat.START) }

        val ivSearch = findViewById<ImageView>(R.id.iv_search)
        ivSearch.setOnClickListener { startActivity(SearchActivity::class.java) }

        fg1 = EquipFragment()
        fg2 = ExclusiveFragment()
        fg3 = BossFragment()
        fg4 = MaterialFragment()
        loadMultipleRootFragment(R.id.fragment_container, 0, fg1, fg2, fg3, fg4)
        checkPermission(0)
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
            sharedPref.isShow = false
            noticeDialog?.dismiss()
        }
        btnIndex.setOnClickListener { noticeDialog?.dismiss() }
    }

    @SuppressLint("CheckResult")
    private fun checkPermission(type: Int) {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        upDateApp(type)
                    } else {
                        "没有读写权限，app部分功能将失效".toast()
                    }
                }
    }

    private fun upDateApp(type: Int) {
        PgyUpdateManager.Builder()
                .setForced(false)                //设置是否强制更新,非自定义回调更新接口此方法有用
                .setUserCanRetry(true)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setDeleteHistroyApk(true)     // 检查更新前是否删除本地历史 Apk， 默认为true
                .setUpdateManagerListener(object : UpdateManagerListener {
                    override fun onNoUpdateAvailable() {
                        dismissLoading()
                        if (type != 0) {
                            "当前是最新版本".toast()
                        } else {
                            if (sharedPref.isShow) {
                                showDialog()
                            }
                        }
                    }

                    override fun onUpdateAvailable(appBean: AppBean) {
                        runOnUiThread {
                            //有更新回调此方法
                            dismissLoading()
                            appBean.releaseNote.logE()
                            showUpdateDialog(appBean)
                        }
                    }

                    override fun checkUpdateFailed(e: Exception) {
                        runOnUiThread {
                            dismissLoading()
                            if (type != 0) {
                                "更新异常".toast()
                            } else {
                                if (sharedPref.isShow && !this@MainActivity.isFinishing) {
                                    showDialog()
                                }
                            }
                        }
                    }
                })
                .register()
    }

    private fun showUpdateDialog(appBean: AppBean) {
        if (Build.VERSION.SDK_INT >= 21) {
            if (dialog == null) {
                dialog = Dialog(mContext, R.style.CustomDialog)
            }
            val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_update, null)
            val tvMsg = view.findViewById<EditText>(R.id.tv_msg)
            val tvSure = view.findViewById<TextView>(R.id.tv_download)
            val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
            tvMsg.setText(appBean.releaseNote)
            tvSure.setOnClickListener {
                dialog?.dismiss()
                LogUtil.e(appBean.downloadURL)
                PgyUpdateManager.downLoadApk(appBean.downloadURL)
            }
            tvCancel.setOnClickListener { dialog!!.dismiss() }
            dialog?.setContentView(view)
            dialog?.setCanceledOnTouchOutside(false)
            dialog?.setCancelable(false)
            dialog?.show()
        } else {
            val dialog = AlertDialog.Builder(mContext)
                    .setMessage(appBean.releaseNote)
                    .setPositiveButton("确定更新") { _, _ -> PgyUpdateManager.downLoadApk(appBean.downloadURL) }
                    .setNegativeButton("取消更新", null)
                    .create()
            dialog.show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_equip -> show = EQUIP
            R.id.nav_career -> show = EXCLUSIVE
            R.id.nav_boss -> show = BOSS
            R.id.nav_task -> show = MATERIAL
            R.id.nav_setting -> {
                startActivity(SettingActivity::class.java)
                return true
            }
            R.id.nav_update -> {
                showLoading()
                checkPermission(1)
            }
            R.id.nav_tips -> {
                Handler().postDelayed({ showDialog() }, 250)
            }

        }
        mDrawerLayout.closeDrawers()
        showHideFragment(getFragment(show), getFragment(hide))
        hide = show

        return true
    }

    private fun getFragment(position: Int): ISupportFragment {
        when (position) {
            EQUIP -> return fg1
            EXCLUSIVE -> return fg2
            BOSS -> return fg3
            MATERIAL -> return fg4
        }
        return fg1
    }

    override fun onBackPressedSupport() {
        val currentBackTime = System.currentTimeMillis()
        if (currentBackTime - lastBackTime < 2000) {
            super.onBackPressed()
            App.getInstance().exitApp()
        } else {
            lastBackTime = currentBackTime
            "再按一次退出".toast()
        }
    }
}

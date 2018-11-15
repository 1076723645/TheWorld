package com.smallcat.theworld.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.pgyersdk.update.PgyUpdateManager
import com.pgyersdk.update.UpdateManagerListener
import com.pgyersdk.update.javabean.AppBean
import com.smallcat.theworld.App
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseActivity
import com.smallcat.theworld.ui.fragment.BossFragment
import com.smallcat.theworld.ui.fragment.EquipFragment
import com.smallcat.theworld.ui.fragment.ExclusiveFragment
import com.smallcat.theworld.ui.fragment.MaterialFragment
import com.smallcat.theworld.utils.fitSystemAllScroll
import com.smallcat.theworld.utils.logE
import com.smallcat.theworld.utils.sharedPref
import com.smallcat.theworld.utils.toast
import com.tbruyelle.rxpermissions2.RxPermissions
import me.yokeyword.fragmentation.ISupportFragment


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var fg1: EquipFragment
    private lateinit var fg2: ExclusiveFragment
    private lateinit var fg3: BossFragment
    private lateinit var fg4: MaterialFragment

    private val EQUIP = 1
    private val EXCLUSIVE = 2
    private val BOSS = 3
    private val MATERIAL = 4

    private var show = EQUIP
    private var hide = EQUIP
    private var lastBackTime = 0L

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun fitSystem() {
        fitSystemAllScroll(this)
    }

    override fun initData() {
        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
        navView.setCheckedItem(R.id.nav_equip)

        mDrawerLayout = findViewById(R.id.drawer_layout)

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
        val dialog = Dialog(this, R.style.CustomDialog)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_notice, null)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        val btnReal = view.findViewById<Button>(R.id.btn_go)
        val btnIndex = view.findViewById<Button>(R.id.btn_cancel)
        dialog.show()
        btnReal.setOnClickListener {
            sharedPref.isShow = false
            dialog.dismiss()
        }
        btnIndex.setOnClickListener { dialog.dismiss() }
    }

    @SuppressLint("CheckResult")
    private fun checkPermission(type: Int){
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        upDateApp(type)
                    } else {
                        "需要读写权限".toast()
                    }
                }
    }

    private fun upDateApp(type: Int){
        PgyUpdateManager.Builder()
                .setForced(false)                //设置是否强制更新,非自定义回调更新接口此方法有用
                .setUserCanRetry(true)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setDeleteHistroyApk(true)     // 检查更新前是否删除本地历史 Apk， 默认为true
                .setUpdateManagerListener(object : UpdateManagerListener {
                    override fun onNoUpdateAvailable() {
                        dismissLoading()
                        if (type != 0) {
                            "当前是最新版本".toast()
                        }else{
                            if (sharedPref.isShow){
                                showDialog()
                            }
                        }
                    }

                    override fun onUpdateAvailable(appBean: AppBean) {
                        //有更新回调此方法
                        dismissLoading()
                        appBean.releaseNote.logE()
                        showUpdateDialog(appBean)
                    }

                    override fun checkUpdateFailed(e: Exception) {
                        runOnUiThread {
                            dismissLoading()
                            if (type != 0) {
                                "更新异常".toast()
                            }else{
                                if (sharedPref.isShow){
                                    showDialog()
                                }
                            }
                        }
                    }
                })
                .register()
    }

    private fun showUpdateDialog(appBean: AppBean){
        val dialog = Dialog(mContext, R.style.CustomDialog)
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_update, null)
        val tvMsg = view.findViewById<EditText>(R.id.tv_msg)
        val tvSure = view.findViewById<TextView>(R.id.tv_download)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        tvMsg.setText(appBean.releaseNote)
        tvSure.setOnClickListener {
            dialog.dismiss()
            PgyUpdateManager.downLoadApk(appBean.downloadURL)
        }
        tvCancel.setOnClickListener{ dialog!!.dismiss() }
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_equip -> show = EQUIP
            R.id.nav_career -> show = EXCLUSIVE
            R.id.nav_boss -> show = BOSS
            R.id.nav_task -> show = MATERIAL
            R.id.nav_setting -> startActivity(SettingActivity::class.java)
            R.id.nav_tips -> showDialog()
            R.id.nav_update -> {
                showLoading()
                checkPermission(1)
            }
        }
        showHideFragment(getFragment(show), getFragment(hide))
        hide = show
        mDrawerLayout.closeDrawers()
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

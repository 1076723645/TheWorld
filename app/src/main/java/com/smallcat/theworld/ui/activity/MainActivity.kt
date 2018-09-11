package com.smallcat.theworld.ui.activity

import android.app.Dialog
import android.content.Intent
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.smallcat.theworld.App
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseActivity
import com.smallcat.theworld.ui.fragment.EquipFragment
import com.smallcat.theworld.utils.ToastUtil
import com.smallcat.theworld.utils.sharedPref
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

    override fun initData() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.setHomeAsUpIndicator(R.drawable.toolbar_head)
        }
        navView.setNavigationItemSelectedListener(this)
        navView.setCheckedItem(R.id.nav_equip)
        if (sharedPref.isShow){
            Handler().postDelayed({ showDialog() }, 500)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    private fun showDialog() {
        val dialog = Dialog(this, R.style.CustomDialog)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_notice, null)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        val btnReal = view.findViewById<Button>(R.id.btn_go)
        val btnIndex = view.findViewById<Button>(R.id.btn_cancel)
        val email = view.findViewById<TextView>(R.id.tv_email)
        email.setTextIsSelectable(true)
        dialog.show()
        btnReal.setOnClickListener {
            sharedPref.isShow = false
            dialog.dismiss()
        }
        btnIndex.setOnClickListener { dialog.dismiss() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_search -> {
                val intent1 = Intent(this, SearchActivity::class.java)
                startActivity(intent1)
            }
            android.R.id.home -> mDrawerLayout.openDrawer(GravityCompat.START)
            R.id.tips -> showDialog()
            R.id.update -> Toast.makeText(this, "更新不可用", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_equip -> show = EQUIP
            R.id.nav_career -> show = EXCLUSIVE
            R.id.nav_boss -> show = BOSS
            R.id.nav_task -> show = MATERIAL
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
            ToastUtil.shortShow("再按一次退出")
        }
    }
}

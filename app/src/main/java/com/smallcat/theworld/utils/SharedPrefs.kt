package com.smallcat.theworld.utils

import android.content.Context
import android.content.SharedPreferences
import com.smallcat.theworld.App

/**
 * @author smallCut
 * @date 2018/4/27
 */
open class SharedPref {
    private val KEY_BLACK = "isBlack"
    private val KEY_FIRST= "isFirst"
    private val KEY_PHONE= "usePhone"
    private var KEY_VERSION = "versionName"
    private var KEY_BACK = "history"

    private val prefs: SharedPreferences = App.getInstance().applicationContext.getSharedPreferences(SharedPref.PREFS_KEY, Context.MODE_PRIVATE)


    companion object {
        fun newInstance() = SharedPref()
        const val PREFS_KEY = "park"
    }

    var isBlack: Boolean
        get() = prefs.getBoolean(KEY_BLACK, true)
        set(token) = prefs.edit().putBoolean(KEY_BLACK, token).apply()

    var versionName: String?
        get() = prefs.getString(KEY_VERSION, "2.5.0")
        set(value) = prefs.edit().putString(KEY_VERSION, value).apply()

    var isShow: Boolean
        get() = prefs.getBoolean(KEY_PHONE, true)
        set(value) = prefs.edit().putBoolean(KEY_PHONE, value).apply()

    //装备是否倒序显示
    var isBack: Boolean
        get() = prefs.getBoolean(KEY_BACK, false)
        set(value) = prefs.edit().putBoolean(KEY_BACK, value).apply()

    var isFirst: Boolean
        get() = prefs.getBoolean(KEY_FIRST, true)
        set(value) = prefs.edit().putBoolean(KEY_FIRST, value).apply()
}

val Context.sharedPref: SharedPref get() = SharedPref.newInstance()
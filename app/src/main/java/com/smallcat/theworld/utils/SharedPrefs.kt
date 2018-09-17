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
    val KEY_FIRST= "isFirst"
    val KEY_PHONE= "usePhone"
    var KEY_TYPE = "userType"
    var KEY_HISTORY = "history"
    var KEY_STATUE = "useState"
    var KEY_USE = "useInfo"
    var KEY_INDEX = "indexData"
    var KEY_SERVICE = "serviceData"

    private val prefs: SharedPreferences = App.getInstance().applicationContext.getSharedPreferences(SharedPref.PREFS_KEY, Context.MODE_PRIVATE)


    companion object {
        fun newInstance() = SharedPref()
        const val PREFS_KEY = "park"
    }

    var isBlack: Boolean
        get() = prefs.getBoolean(KEY_BLACK, false)
        set(token) = prefs.edit().putBoolean(KEY_BLACK, token).apply()

    var versionName: String
        get() = prefs.getString(KEY_TYPE, "2.0")
        set(value) = prefs.edit().putString(KEY_TYPE, value).apply()

    var isShow: Boolean
        get() = prefs.getBoolean(KEY_PHONE, true)
        set(value) = prefs.edit().putBoolean(KEY_PHONE, value).apply()

    var userId: Int
        get() = prefs.getInt(KEY_HISTORY, 0)
        set(value) = prefs.edit().putInt(KEY_HISTORY, value).apply()

    var isFirst: Boolean
        get() = prefs.getBoolean(KEY_FIRST, true)
        set(value) = prefs.edit().putBoolean(KEY_FIRST, value).apply()
}

val Context.sharedPref: SharedPref get() = SharedPref.newInstance()
package com.smallcat.theworld.utils

import android.content.Context
import android.content.SharedPreferences
import com.smallcat.theworld.App

/**
 * @author smallCut
 * @date 2018/4/27
 */
class SharedPref {

    private val prefs: SharedPreferences = App.getInstance().applicationContext.getSharedPreferences(SharedPref.PREFS_KEY, Context.MODE_PRIVATE)

    companion object {
        private const val KEY_CHOOSE_RECORD = "chooseRecord"
        private const val KEY_BLACK = "isBlack"
        private const val KEY_FIRST= "isFirst"
        private const val KEY_PHONE= "usePhone"
        private const val KEY_VERSION = "versionName"
        private const val KEY_BACK = "history"
        private const val KEY_RECORD = "record"
        private const val PREFS_KEY = "park"

        fun newInstance() = SharedPref()
    }

    var isBlack: Boolean
        get() = prefs.getBoolean(KEY_BLACK, false)
        set(token) = prefs.edit().putBoolean(KEY_BLACK, token).apply()

    var versionName: String?
        get() = prefs.getString(KEY_VERSION, "2.10.5")
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

    //记录恢复
    var recoveryRecord: Boolean
        get() = prefs.getBoolean(KEY_FIRST, false)
        set(value) = prefs.edit().putBoolean(KEY_FIRST, value).apply()

    var chooseId: Long
        get() = prefs.getLong(KEY_CHOOSE_RECORD, -1L)
        set(value) = prefs.edit().putLong(KEY_CHOOSE_RECORD, value).apply()
}

val Context.sharedPref: SharedPref get() = SharedPref.newInstance()
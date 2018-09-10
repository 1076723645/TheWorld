package com.smallcat.theworld.utils

import android.content.Context
import android.content.SharedPreferences
import com.smallcat.theworld.App

/**
 * @author smallCut
 * @date 2018/4/27
 */
open class SharedPref {
    private val KEY_TOKEN = "token"
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

    var token: String
        get() = prefs.getString(KEY_TOKEN, "")
        set(token) = prefs.edit().putString(KEY_TOKEN, token).apply()

    //用户类型： 企业员工，企业管理员
    var userType: String
        get() = prefs.getString(KEY_TYPE, "")
        set(value) = prefs.edit().putString(KEY_TYPE, value).apply()

    // 认证状态（1未认证2认证中3认证通过 4认证驳回）
    var userConfirmState: Int
        get() = prefs.getInt(KEY_STATUE, 0)
        set(value) = prefs.edit().putInt(KEY_STATUE, value).apply()

    var phone: String
        get() = prefs.getString(KEY_PHONE, "")
        set(value) = prefs.edit().putString(KEY_PHONE, value).apply()

    var userId: Int
        get() = prefs.getInt(KEY_HISTORY, 0)
        set(value) = prefs.edit().putInt(KEY_HISTORY, value).apply()

    //用户信息缓存
    var userInfo: String
        get() = prefs.getString(KEY_USE, "")
        set(value) = prefs.edit().putString(KEY_USE, value).apply()

    var isFirst: Boolean
        get() = prefs.getBoolean(KEY_FIRST, true)
        set(value) = prefs.edit().putBoolean(KEY_FIRST, value).apply()

    //首页缓存
    var indexData: String
        get() = prefs.getString(KEY_INDEX, "")
        set(value) = prefs.edit().putString(KEY_INDEX, value).apply()

    //服务列表
    var serviceData: String
        get() = prefs.getString(KEY_SERVICE, "")
        set(value) = prefs.edit().putString(KEY_SERVICE, value).apply()
}

val Context.sharedPref: SharedPref get() = SharedPref.newInstance()
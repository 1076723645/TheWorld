package com.smallcat.theworld.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

/**
 * Created by smallCut on 2018/6/4.
 */
fun fitSystemAllScroll(activity: Activity) {
    val decorView = activity.window.decorView
    if (Build.VERSION.SDK_INT >= 21) {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        activity.window.statusBarColor = Color.TRANSPARENT
    }
}

fun fitSystemWhite(activity: Activity) {
    val decorView = activity.window.decorView
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        activity.window.statusBarColor = Color.TRANSPARENT
    } else {
        fitSystemAllScroll(activity)
    }
}

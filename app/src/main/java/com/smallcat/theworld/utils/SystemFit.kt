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
    if (Build.VERSION.SDK_INT >= 21) {
        val decorView = activity.window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        activity.window.statusBarColor = Color.TRANSPARENT
    }
}

fun fitSystemWhite(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decorView = activity.window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        activity.window.statusBarColor = Color.TRANSPARENT
    } else {
        fitSystemAllScroll(activity)
    }
}

fun fullScreen(activity: Activity) {
    val window = activity.window
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_FULLSCREEN
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val lp = window.attributes
        // 仅当缺口区域完全包含在状态栏之中时，才允许窗口延伸到刘海区域显示
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
        // 永远不允许窗口延伸到刘海区域
        //            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
        // 始终允许窗口延伸到屏幕短边上的刘海区域
        //lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        window.attributes = lp
    }
}


package com.smallcat.theworld.utils

import android.app.Activity
import android.content.res.Resources
import com.smallcat.theworld.App

/**
 * @author smallCut
 * @date 2018/9/28
 */
/**
 * Adapt the screen for vertical slide.
 *
 * @param activity        The activity.
 * @param designWidthInPx The size of design diagram's width, in pixel.
 */
fun adaptScreen4VerticalSlide(activity: Activity,
                              designWidthInPx: Int) {
    adaptScreen(activity, designWidthInPx, true)
}

/**
 * Adapt the screen for horizontal slide.
 *
 * @param activity         The activity.
 * @param designHeightInPx The size of design diagram's height, in pixel.
 */
fun adaptScreen4HorizontalSlide(activity: Activity,
                                designHeightInPx: Int) {
    adaptScreen(activity, designHeightInPx, false)
}

/**
 * Reference from: https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
 */
private fun adaptScreen(activity: Activity,
                        sizeInPx: Int,
                        isVerticalSlide: Boolean) {
    val systemDm = Resources.getSystem().displayMetrics
    val appDm = App.getInstance().resources.displayMetrics
    val activityDm = activity.resources.displayMetrics
    if (isVerticalSlide) {
        activityDm.density = activityDm.widthPixels / sizeInPx.toFloat()
    } else {
        activityDm.density = activityDm.heightPixels / sizeInPx.toFloat()
    }
    activityDm.scaledDensity = activityDm.density * (systemDm.scaledDensity / systemDm.density)
    activityDm.densityDpi = (160 * activityDm.density).toInt()

    appDm.density = activityDm.density
    appDm.scaledDensity = activityDm.scaledDensity
    appDm.densityDpi = activityDm.densityDpi

    ADAPT_SCREEN_ARGS.sizeInPx = sizeInPx
    ADAPT_SCREEN_ARGS.isVerticalSlide = isVerticalSlide
}

val ADAPT_SCREEN_ARGS = AdaptScreenArgs()

class AdaptScreenArgs {
    var sizeInPx: Int = 0
    var isVerticalSlide: Boolean = false
}

/**
 * Cancel adapt the screen.
 *
 * @param activity The activity.
 */
fun cancelAdaptScreen(activity: Activity) {
    val systemDm = Resources.getSystem().displayMetrics
    val appDm = App.getInstance().resources.displayMetrics
    val activityDm = activity.resources.displayMetrics
    activityDm.density = systemDm.density
    activityDm.scaledDensity = systemDm.scaledDensity
    activityDm.densityDpi = systemDm.densityDpi

    appDm.density = systemDm.density
    appDm.scaledDensity = systemDm.scaledDensity
    appDm.densityDpi = systemDm.densityDpi
}

/**
 * Return whether adapt screen.
 *
 * @return `true`: yes<br></br>`false`: no
 */
fun isAdaptScreen(): Boolean {
    val systemDm = Resources.getSystem().displayMetrics
    val appDm = App.getInstance().resources.displayMetrics
    return systemDm.density != appDm.density
}
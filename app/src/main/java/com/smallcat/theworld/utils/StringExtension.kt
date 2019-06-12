package com.smallcat.theworld.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import android.widget.Toast
import com.smallcat.theworld.App

/**
 * @author hui
 * @date 2018/4/27
 */
fun String.logD() {
    LogUtil.d(this)
}

fun String.logE() {
    LogUtil.e(this)
}

private var toast: Toast? = null

@SuppressLint("ShowToast")
fun String.toast() {
    if (toast == null) {
        toast = Toast.makeText(App.getInstance().applicationContext, this, Toast.LENGTH_SHORT)
    } else {
        toast!!.setText(this)
    }
    toast!!.show()
}

fun Context.start(activity: Class<*>) {
    Intent(applicationContext, activity).apply {
        startActivity(this)
    }
}

fun String.hide4(): String{
    val subString = this.substring(3, 7)
    return this.replace(subString, "****")
}

fun Context.getResourceColor(color:Int) = ContextCompat.getColor(this, color)

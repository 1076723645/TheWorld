package com.smallcat.theworld.utils

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.smallcat.theworld.model.callback.SureCallBack
import com.smallcat.theworld.ui.fragment.SureDialogFragment

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

fun String.toast() {
    ToastUtil.shortShow(this)
}

fun String.toastLong() {
    ToastUtil.longShow(this)
}

fun Context.start(activity: Class<*>) {
    Intent(applicationContext, activity).apply {
        startActivity(this)
    }
}

fun String.hide4(): String {
    val subString = this.substring(3, 7)
    return this.replace(subString, "****")
}

fun Context.getResourceColor(color: Int) = ContextCompat.getColor(this, color)

fun AppCompatActivity.showCheckDialog(text: String, callback: SureCallBack) {
    val dialog = SureDialogFragment(text, callback)
    dialog.show(supportFragmentManager, dialog.tag)
}
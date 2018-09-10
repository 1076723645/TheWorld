package com.smallcat.theworld.utils

import android.annotation.SuppressLint
import android.widget.Toast
import com.smallcat.theworld.App

/**
 * @author smallCut
 * @date 2018/4/27
 */
object ToastUtil {

    fun toast(s: String) {
        Toast.makeText(App.getInstance().applicationContext, s, Toast.LENGTH_SHORT).show()
    }

    private var toast: Toast? = null

    @SuppressLint("ShowToast")
    fun shortShow(msg: String) {
        if (toast == null) {
            toast = Toast.makeText(App.getInstance().applicationContext, msg, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(msg)
        }
        toast!!.show()
    }

}

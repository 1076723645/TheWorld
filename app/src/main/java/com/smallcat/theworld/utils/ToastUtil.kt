package com.smallcat.theworld.utils

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import com.smallcat.theworld.App

/**
 * @author smallCut
 * @date 2018/4/27
 */
object ToastUtil {

    fun toast(s: String) {
        Toast.makeText(App.getInstance(), s, Toast.LENGTH_SHORT).show()
    }

    private var mToast: Toast? = null

    @SuppressLint("ShowToast")
    fun shortShow(s: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //9.0以上toast直接用原生的方法即可，并不用setText防止重复的显示的问题
            Toast.makeText(App.getInstance(), s, Toast.LENGTH_SHORT).show()
        } else {
            if (mToast == null) {
                mToast = Toast.makeText(App.getInstance(), s, Toast.LENGTH_SHORT)
            } else {
                mToast!!.duration = Toast.LENGTH_SHORT
                mToast!!.setText(s)
            }
            mToast!!.show()
        }
    }

}

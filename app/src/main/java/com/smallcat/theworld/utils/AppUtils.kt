package com.smallcat.theworld.utils

import android.content.Context
import android.content.pm.PackageManager
import com.smallcat.theworld.R
import java.util.*

/**
 * @author smallCut
 * @date 2018/9/10
 */
object AppUtils{

    fun getVerName(context: Context): String {
        var verName = ""
        try {
            verName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return verName
    }

    fun getColor(qul: String): Int {
        return when {
            qul.contains("罕见") -> R.color.lightskyblue
            qul.contains("天绝史诗") -> R.color.mediumslateblue
            qul.contains("传奇至宝") -> R.color.royalblue
            qul.contains("神话传说") -> R.color.darkmagenta
            qul.contains("冥灵传世") -> R.color.btn_b
            else -> R.color.firebrick
        }
    }

    fun needEquip(s: String): List<String> {
        val access = s.replace("\n".toRegex(), "")//去掉回车
        var a = access.split("\\+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()//按+分割
        val sb = StringBuilder()
        for (i in a.indices) {//去掉首尾空格
            if (a[i].trim { it <= ' ' } == "1") {//解决+1装备被分割的问题
                sb.deleteCharAt(sb.length - 1)
                sb.append(" +1")
            } else {
                sb.append(a[i].trim { it <= ' ' })
            }
            if (i != a.size - 1) {
                sb.append(";")
            }
        }
        a = sb.toString().split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return Arrays.asList(*a)
    }
}
package com.smallcat.theworld.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.Boss
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.model.db.Exclusive
import com.smallcat.theworld.ui.activity.BossDetailActivity
import com.smallcat.theworld.ui.activity.EquipDetailActivity
import org.litepal.crud.DataSupport
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

    fun getColor(mContext:Context, qul: String): Int {
        val color= when{
            qul.contains("罕见") -> R.color.lightskyblue
            qul.contains("天绝史诗") -> R.color.mediumslateblue
            qul.contains("传奇至宝") -> R.color.royalblue
            qul.contains("神话传说") -> R.color.darkmagenta
            qul.contains("冥灵传世") -> R.color.btn_b
            else -> R.color.firebrick
        }
        return getResourcesColor(mContext, color)
    }

    fun getResourcesColor(mContext:Context, color: Int) = ContextCompat.getColor(mContext, color)

    fun getEmptyView(mContext:Context, s: String) : View {
        val view = LayoutInflater.from(mContext).inflate(R.layout.empty_view, null)
        val text = view.findViewById<TextView>(R.id.tv_none)
        text.text = s
        return view
    }

    fun goEquipDetailActivity(mContext:Context, name: String) : Boolean{
        val data = DataSupport.select("id")
                .where("equipName = ?", name).find(Equip::class.java)
        if (data.isNotEmpty()){
            val intent = Intent(mContext, EquipDetailActivity::class.java)
            intent.putExtra("id", data[0].id.toString())
            mContext.startActivity(intent)
            return true
        }
        return false
    }

    fun goBossDetailActivity(mContext:Context, name: String) : Boolean{
        val data = DataSupport.select("id")
                .where("bossName like ?", "%$name%").find(Boss::class.java)
        if (data.isNotEmpty()){
            val intent = Intent(mContext, BossDetailActivity::class.java)
            intent.putExtra("id", data[0].id.toString())
            mContext.startActivity(intent)
            return true
        }
        return false
    }


    fun clean(){
        DataSupport.deleteAll(Equip::class.java)
        DataSupport.deleteAll(Boss::class.java)
        DataSupport.deleteAll(Exclusive::class.java)
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
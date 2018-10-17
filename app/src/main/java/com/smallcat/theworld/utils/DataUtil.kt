package com.smallcat.theworld.utils

import android.content.Context
import android.support.design.widget.TabLayout
import android.widget.LinearLayout
import android.widget.TextView

/**
 * @author smallCut
 * @date 2018/9/13
 */
object DataUtil{

    private val professionList = arrayOf("神射手", "狙击手", "雷霆行者", "追星剑圣", "刺客", "赏金猎人", "机械师", "拳师", "旅行商人", "收割者",
            "附魔师", "魅影十字军", "圣光十字军", "狂战士", "黑暗骑士", "魔枪斗士", "剑之骑士", "冰法", "火法", "月法", "风法", "精灵召唤师", "灵魂织女",
            "牧师", "炼金术士", "血法", "巫术师", "电法")

    fun getProfession(position:Int) = professionList[position]

    fun reflex(tabLayout: TabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post {
            try {
                //拿到tabLayout的mTabStrip属性
                val mTabStrip = tabLayout.getChildAt(0) as LinearLayout

                val dp10 = dip2px(tabLayout.context, 20f)

                for (i in 0 until mTabStrip.childCount) {
                    val tabView = mTabStrip.getChildAt(i)

                    //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                    val mTextViewField = tabView.javaClass.getDeclaredField("mTextView")
                    mTextViewField.isAccessible = true

                    val mTextView = mTextViewField.get(tabView) as TextView

                    tabView.setPadding(0, 0, 0, 0)

                    //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                    var width = mTextView.width
                    if (width == 0) {
                        mTextView.measure(0, 0)
                        width = mTextView.measuredWidth
                    }

                    //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                    val params = tabView.layoutParams as LinearLayout.LayoutParams
                    params.width = width
                    params.leftMargin = dp10
                    params.rightMargin = dp10
                    tabView.layoutParams = params
                    tabView.invalidate()
                }

            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    private fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}
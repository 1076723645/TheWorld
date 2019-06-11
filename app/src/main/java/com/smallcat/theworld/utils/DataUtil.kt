package com.smallcat.theworld.utils

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.support.design.widget.TabLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import java.util.*

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


    /**
     * 生成一个伪随机数
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random.nextInt
     */
    fun randInt(min: Int, max: Int): Int {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        val rand = Random()

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive

        return rand.nextInt(max - min + 1) + min
    }

    /**
     * 选择图片
     */
    fun addPicture(mActivity: Activity, max: Int, code: Int){
        PictureSelector.create(mActivity)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(max)// 最大图片选择数量 int
                .imageSpanCount(3)// 每行显示个数 int
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .sizeMultiplier(0.4f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .cropCompressQuality(50)
                .compress(true)// 是否压缩 true or false
                .compressSavePath(mActivity.getExternalFilesDir(null)?.toString())//压缩图片保存地址
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(code)//结果回调onActivityResult code
    }
}
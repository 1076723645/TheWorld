package com.smallcat.theworld.model

import com.smallcat.theworld.R

/**
 * @author smallCut
 * @date 2018/9/19
 */

fun getHeroImg(name: String):Int {
    when(name){
        "魅影十字军" -> return R.drawable.ic_my
        "圣光十字军" -> return R.drawable.ic_dc
        "魔枪斗士" -> return R.drawable.ic_mq
        "狂战士" -> return R.drawable.ic_kz
        "剑之骑士" -> return R.drawable.ic_c8
        "旅行商人" -> return R.drawable.ic_bbx
        "黑暗骑士" -> return R.drawable.ic_hq
        "机械师" -> return R.drawable.ic_jx
        "雷霆行者" -> return R.drawable.ic_33
        "拳师" -> return R.drawable.ic_qs
        "无极剑魂" -> return R.drawable.ic_jh
        "追星剑圣" -> return R.drawable.ic_js
        "神射手" -> return R.drawable.ic_ss
        "狙击手" -> return R.drawable.ic_jj
        "刺客" -> return R.drawable.ic_ck
        "附魔师" -> return R.drawable.ic_fm
        "收割者" -> return R.drawable.ic_sgz
        "赏金猎人" -> return R.drawable.ic_sj
        "火法" -> return R.drawable.ic_hf
        "冰法" -> return R.drawable.ic_bf
        "风法" -> return R.drawable.ic_ff
        "月法" -> return R.drawable.ic_yf
        "电法" -> return R.drawable.ic_df
        "牧师" -> return R.drawable.ic_ms
        "精灵召唤师" -> return R.drawable.ic_zh
        "灵魂织女" -> return R.drawable.ic_zn
        "巫术师" -> return R.drawable.ic_wss
        "炼金术士" -> return R.drawable.ic_lj
        "血法" -> return R.drawable.ic_xf
        "幽人" -> return R.drawable.ic_youren
        "女巫" -> return R.drawable.ic_nvwu
    }
    return R.drawable.ic_ss
}



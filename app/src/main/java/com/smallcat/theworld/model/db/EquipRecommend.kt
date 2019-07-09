package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport
import java.util.ArrayList

/**
 * @author smallCut
 * @date 2018/10/15
 */
class EquipRecommend(
        var id: Long = 0,
        var heroName: String = "",//英雄名称
        var equipEarly: List<String> = ArrayList(),//过渡装备
        var resonEarly: String = "",//过渡理由
        var equipFinal: List<String> = ArrayList(),//后期装备
        var resonFinal: String = "",//理由
        var auther: String = "0",//作者
        var action: String = "0",//输出手法
        var title: String = "0"//标题
) : DataSupport()
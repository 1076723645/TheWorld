package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport
import java.util.ArrayList

/**
 * Created by smallCat on 2018/9/10.
 */
class Equip(
        var id: Int = 0,
        var equipName: String = "",//装备名
        var quality: String = "",//品质
        var level: String = "0",//等级
        var equipmentProperty: String = "",//属性
        var dataList: List<String> = ArrayList(),//合成列表
        var advanceList: List<String> = ArrayList(), //可合成列表
        var access: String = "",//获取方法
        var exclusive: String = "",//专属
        var type: String = "",//种类
        var isAdd: Int = 0,//收藏 0没有 1添加
        var imgId: Int = 0
) : DataSupport()

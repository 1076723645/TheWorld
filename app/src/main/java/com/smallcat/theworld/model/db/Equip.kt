package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport
import java.util.ArrayList

/**
 * Created by smallCat on 2018/9/10.
 */
class Equip : DataSupport() {

    var id: Int = 0
    var equipName: String = ""//装备名
    var quality: String = ""//品质
    var level: String = ""//等级
    var equipmentProperty: String = ""//属性
    var dataList: List<String> = ArrayList()//装备数据
    var access: String = ""//获取
    var exclusive: String = ""//专属
    var type: String = ""//种类
    var imgId: Int = 0
}

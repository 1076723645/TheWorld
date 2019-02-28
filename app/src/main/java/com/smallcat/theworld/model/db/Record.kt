package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport

class Record : DataSupport() {

    var id: Int = 0
    var heroName: String? = null
        get() = if (field == null) "" else field//职业名
    var type: String? = null
        get() = if (field == null) "" else field//攻击类型
    var main: String? = null
        get() = if (field == null) "" else field//主属性
    var distance: String? = null
        get() = if (field == null) "" else field//攻击距离
    var position: String? = null
        get() = if (field == null) "" else field//定位
    var speed: String? = null
        get() = if (field == null) "" else field//移动速度
    var back: String? = null
        get() = if (field == null) "" else field//背景故事
    var imgId: Int = 0

}
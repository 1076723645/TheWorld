package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport

class RecordThing : DataSupport() {

    var id: Long = 0
    var recordId: Long = 0//属于哪个记录
    var equipName: String? = null
        get() = if (field == null) "" else field//装备名称
    var equipImg: Int = 0//装备图片
    var type: Int = 0//类别 1期望 2拥有
    var number: Int = 1//拥有数量
    var part: String = ""//部位

}
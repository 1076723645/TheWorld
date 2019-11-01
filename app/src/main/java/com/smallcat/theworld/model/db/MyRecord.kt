package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport
//所有存档
class MyRecord : DataSupport() {

    var id: Long = 0
    var heroImg: Int = 0                         //英雄图片
    var heroName: String? = null
        get() = if (field == null) "" else field //职业名
    var recordLevel: Int = 0                     //存档等级
    var createTime: Long = 0                    //创建时间
    var updateTime: Long = 0                    //更新时间
    var isDefault: Boolean = false              //默认选中
    var recordCode: String? = null
        get() = if (field == null) "" else field//存档代码

}
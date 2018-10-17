package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport

/**
 * 专属
 * @author smallCut
 * @date 2018/9/10
 */
class Exclusive : DataSupport() {

    var id: Int = 0
    var equipName: String? = null
        get() = if (field == null) "" else field//装备名
    var imgId: Int = 0 //装备图标
    var profession: String? = null
        get() = if (field == null) "" else field//职业
    var skill: String? = null
        get() = if (field == null) "" else field//强化技能
    var effect: String? = null
        get() = if (field == null) "" else field//强化效果
}

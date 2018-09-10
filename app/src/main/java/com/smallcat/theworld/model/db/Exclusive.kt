package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport

/**
 * @author smallCut
 * @date 2018/9/10
 */
class Exclusive : DataSupport() {

    var id: Int = 0
    var equipName: String = ""//装备名
    var profession: String = ""//职业
    var skill: String = ""//强化技能
    var effect: String = ""//强化效果
}
package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport

/**
 * @author smallCut
 * @date 2018/9/10
 */
class Boss : DataSupport() {

    var id: Int = 0
    var bossName: String? = null
        get() = if (field == null) "" else field//boss名
    var drop: String? = null
        get() = if (field == null) "" else field//掉落
    var call: String? = null
        get() = if (field == null) "" else field//召唤方式
    var hp: String? = null
        get() = if (field == null) "" else field//血量
    var power: String? = null
        get() = if (field == null) "" else field//攻击
    var nail: String? = null
        get() = if (field == null) "" else field//护甲
    var distance: String? = null
        get() = if (field == null) "" else field//攻击距离
    var level: String? = null
        get() = if (field == null) "" else field//等级
    var skill: String? = null
        get() = if (field == null) "" else field//技能
    var resistance: String? = null
        get() = if (field == null) "" else field//魔抗
    var strategy: String? = null
        get() = if (field == null) "" else field//策略
    var imgId: Int = 0
}



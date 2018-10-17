package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport

/**
 * @author smallCut
 * @date 2018/10/16
 */
class Skill: DataSupport(){

    var id:Int = 0
    var heroName:String? = null
        get() = if (field == null) "" else field//职业
    var skillName:String? = null
        get() = if (field == null) "" else field//技能名称
    var skillKey:String? = null
        get() = if (field == null) "" else field//按键
    var skillEffect:String? = null
        get() = if (field == null) "" else field//技能效果
    var imgId:Int = 0
}
package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport
import java.util.ArrayList

/**
 * @author smallCut
 * @date 2018/10/15
 */
class Hero : DataSupport() {

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
    /*  var exclusives:List<Exclusive>? = null
          get() = if (field == null) { ArrayList() } else field //专属
      var skills:List<Skill>? = null
          get() = if (field == null) { ArrayList() } else field //技能*/
    var imgId: Int = 0

}
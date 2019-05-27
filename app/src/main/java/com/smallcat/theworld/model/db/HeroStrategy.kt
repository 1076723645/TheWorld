package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport

/**
 * @author smallCut
 * @date 2018/10/15
 */
class HeroStrategy(
        var id: Int = 0,
        var heroName: String = "",//英雄名称
        var address: String = "",//地址
        var auther: String = "0",//作者
        var version: String = "0",//版本
        var title: String = "0"//标题
) : DataSupport()
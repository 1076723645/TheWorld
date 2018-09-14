package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport
import java.util.ArrayList

/**
 * @author smallCut
 * @date 2018/9/10
 */
class Material : DataSupport() {

    var id: Int = 0
    var materialName: String = ""//材料名
    var access: String = ""//获取
    var effect: String = ""//效果
    var dataList: List<String> = ArrayList() //获取途径，掉落
    var advanceList: List<String> = ArrayList() //可合成列表
    var type: String = ""
    var imgId: Int = 0
}

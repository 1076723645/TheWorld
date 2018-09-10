package com.smallcat.theworld.model.db

import org.litepal.crud.DataSupport

/**
 * @author smallCut
 * @date 2018/9/10
 */
class Material : DataSupport() {

    var id: Int = 0
    var materialName: String = ""//材料名
    var access: String = ""//获取
    var effect: String = ""//效果
    var type: String = ""
    var imgId: Int = 0
}

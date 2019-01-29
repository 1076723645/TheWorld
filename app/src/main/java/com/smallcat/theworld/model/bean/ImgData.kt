package com.smallcat.theworld.model.bean

import java.io.Serializable

/**
 * @author smallCut
 * @date 2018/9/12
 */
class ImgData : Serializable {

    var name = ""
    var imgUrl: Int = 0
    var height: Int = 0
    val isError: Boolean = false
}

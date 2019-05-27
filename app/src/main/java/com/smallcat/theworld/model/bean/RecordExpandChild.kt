package com.smallcat.theworld.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.smallcat.theworld.ui.adapter.RecordExpandAdapter

class RecordExpandChild : MultiItemEntity {

    var number = 0
    var equipName = ""
    var equipIcon: Int = 0
    var isChoose = false

    override fun getItemType(): Int = RecordExpandAdapter.TYPE_CONTENT

}
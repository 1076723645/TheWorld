package com.smallcat.theworld.model.bean

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.smallcat.theworld.ui.adapter.RecordExpandAdapter

class RecordExpandData : AbstractExpandableItem<RecordExpandChild>(), MultiItemEntity {

    var equipName = ""
    var equipIcon: Int = 0
    var number: Int = 0
    var list = ArrayList<RecordExpandChild>()
    var dataList = ArrayList<String>()

    override fun getLevel(): Int = -1

    override fun getItemType(): Int = RecordExpandAdapter.TYPE_HEAD

}
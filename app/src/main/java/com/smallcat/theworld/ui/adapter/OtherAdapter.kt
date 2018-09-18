package com.smallcat.theworld.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.model.db.Material

/**
 * @author smallCut
 * @date 2018/9/14
 */
class OtherAdapter(data: List<Equip>) : BaseQuickAdapter<Equip, BaseViewHolder>(R.layout.item_material, data) {

    override fun convert(viewHolder: BaseViewHolder, item: Equip) {
        viewHolder.setText(R.id.tv_name, item.equipName)
                .setImageResource(R.id.iv_material, item.imgId)
    }
}
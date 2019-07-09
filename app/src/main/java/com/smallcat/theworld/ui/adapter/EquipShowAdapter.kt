package com.smallcat.theworld.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.Equip

/**
 * Created by smallCut on 2018/7/19.
 */
class EquipShowAdapter(data: MutableList<Equip>?) : BaseQuickAdapter<Equip, BaseViewHolder>(R.layout.item_record_equip_show, data) {

    override fun convert(viewHolder: BaseViewHolder, item: Equip) {
        viewHolder.setText(R.id.tv_name, item.equipName)

        if (item.imgId != 0) {
            viewHolder.setImageResource(R.id.iv_img, item.imgId)
        } else {
            viewHolder.setImageResource(R.id.iv_img, R.color.color_cc)
        }
    }

}
package com.smallcat.theworld.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.Material

/**
 * @author smallCut
 * @date 2018/9/14
 */
class OtherAdapter(data: List<Material>) : BaseQuickAdapter<Material, BaseViewHolder>(R.layout.item_material, data) {

    override fun convert(viewHolder: BaseViewHolder, item: Material) {
        viewHolder.setText(R.id.tv_name, item.materialName)
                .setImageResource(R.id.iv_material, item.imgId)
    }
}
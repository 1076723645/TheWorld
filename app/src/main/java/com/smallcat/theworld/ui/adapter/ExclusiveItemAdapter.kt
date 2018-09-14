package com.smallcat.theworld.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.Exclusive

/**
 * @author smallCut
 * @date 2018/9/13
 */
class ExclusiveItemAdapter(data: MutableList<Exclusive>?) : BaseQuickAdapter<Exclusive, BaseViewHolder>(R.layout.item_exclusive, data) {

    override fun convert(viewHolder: BaseViewHolder, item: Exclusive) {
        viewHolder.setText(R.id.tv_exclusive_name,   "${viewHolder.layoutPosition+1}." + item.equipName)
                .setText(R.id.tv_exclusive, item.effect)
    }
}
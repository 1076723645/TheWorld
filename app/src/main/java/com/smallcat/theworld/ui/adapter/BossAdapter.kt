package com.smallcat.theworld.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.Boss

/**
 * @author smallCut
 * @date 2018/9/13
 */
class BossAdapter(data: List<Boss>) : BaseQuickAdapter<Boss, BaseViewHolder>(R.layout.item_list_access, data) {

    override fun convert(viewHolder: BaseViewHolder, item: Boss) {
        viewHolder.setText(R.id.tv_name, item.bossName)
    }
}
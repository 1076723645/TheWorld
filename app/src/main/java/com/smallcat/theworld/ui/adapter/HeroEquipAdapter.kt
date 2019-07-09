package com.smallcat.theworld.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.Boss
import com.smallcat.theworld.model.db.EquipRecommend
import com.smallcat.theworld.model.db.HeroStrategy

/**
 * @author smallCut
 * @date 2018/9/13
 */
class HeroEquipAdapter(data: List<EquipRecommend>?) : BaseQuickAdapter<EquipRecommend, BaseViewHolder>(R.layout.item_strategy, data) {

    override fun convert(viewHolder: BaseViewHolder, item: EquipRecommend) {
        viewHolder.setText(R.id.tv_title, item.title)
                .setText(R.id.tv_auther, "by- ${item.auther}")
    }
}
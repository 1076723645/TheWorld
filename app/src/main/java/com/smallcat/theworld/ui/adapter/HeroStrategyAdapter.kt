package com.smallcat.theworld.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.Boss
import com.smallcat.theworld.model.db.HeroStrategy

/**
 * @author smallCut
 * @date 2018/9/13
 */
class HeroStrategyAdapter(data: List<HeroStrategy>?) : BaseQuickAdapter<HeroStrategy, BaseViewHolder>(R.layout.item_strategy, data) {

    override fun convert(viewHolder: BaseViewHolder, item: HeroStrategy) {
        viewHolder.setText(R.id.tv_title, item.title)
                .setText(R.id.tv_auther, item.auther)
    }
}
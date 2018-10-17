package com.smallcat.theworld.ui.adapter

import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.utils.AppUtils

/**
 * Created by smallCut on 2018/7/19.
 */
class EquipAdapter(data: List<Equip>?) : BaseQuickAdapter<Equip, BaseViewHolder>(R.layout.item_equip, data) {

    override fun convert(viewHolder: BaseViewHolder, item: Equip) {
        viewHolder.setText(R.id.tv_name, item.equipName)
                .setText(R.id.tv_level, "${item.level}级")

        val tvQul = viewHolder.getView<TextView>(R.id.tv_qul)
        tvQul.text = item.quality
        tvQul.setTextColor(AppUtils.getColor(mContext, item.quality))
        Glide.with(mContext).load(item.imgId).into(viewHolder.getView(R.id.iv_equip))
    }
}
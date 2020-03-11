package com.smallcat.theworld.ui.adapter

import android.widget.TextView
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
        tvQul.setTextColor(AppUtils.getEquipQualityColor(mContext, item.quality))
        if (item.imgId != 0) {
            viewHolder.setImageResource(R.id.iv_equip, item.imgId)
        } else {
            viewHolder.setImageResource(R.id.iv_equip, R.color.color_cc)
        }
    }
}
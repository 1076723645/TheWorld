package com.smallcat.theworld.ui.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.RecordThing

/**
 * Created by smallCut on 2018/7/19.
 */
class RecordEquipShowAdapter(data: MutableList<RecordThing>?) : BaseQuickAdapter<RecordThing, BaseViewHolder>(R.layout.item_record_equip_show, data) {

    override fun convert(viewHolder: BaseViewHolder, item: RecordThing?) {
        item?.let {
            viewHolder.setText(R.id.tv_name, item.equipName)
            if (item.equipImg != 0) {
                viewHolder.setImageResource(R.id.iv_img, item.equipImg)
            } else {
                viewHolder.setImageResource(R.id.iv_img, R.color.color_cc)
            }

            val tvNumber = viewHolder.getView<TextView>(R.id.tv_number)
            if (item.number > 1) {
                tvNumber?.text = "${item.number}"
                tvNumber?.visibility = View.VISIBLE
            } else {
                tvNumber?.visibility = View.GONE
            }
        }
    }

}
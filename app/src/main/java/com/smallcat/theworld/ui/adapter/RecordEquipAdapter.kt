package com.smallcat.theworld.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.RecordThing

/**
 * Created by smallCut on 2018/7/19.
 */
class RecordEquipAdapter(data: MutableList<RecordThing>) : BaseQuickAdapter<RecordThing, BaseViewHolder>(R.layout.item_record_equip, data) {

    override fun convert(viewHolder: BaseViewHolder, item: RecordThing) {
        viewHolder.setImageResource(R.id.iv_img, item.equipImg)
                .setText(R.id.tv_name, item.equipName)
                .addOnClickListener(R.id.iv_delete)

        val tvNumber = viewHolder.getView<TextView>(R.id.tv_number)
        if (item.number > 1) {
            tvNumber.text = "${item.number}"
            tvNumber.visibility = View.VISIBLE
        } else {
            tvNumber.visibility = View.GONE
        }
    }

}
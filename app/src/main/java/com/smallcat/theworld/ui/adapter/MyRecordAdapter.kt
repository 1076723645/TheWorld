package com.smallcat.theworld.ui.adapter

import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.MyRecord
import com.smallcat.theworld.model.getHeroImg
import com.smallcat.theworld.utils.AppUtils

/**
 * Created by smallCut on 2018/7/19.
 */
class MyRecordAdapter(data: MutableList<MyRecord>?) : BaseQuickAdapter<MyRecord, BaseViewHolder>(R.layout.item_record_pro, data) {

    override fun convert(viewHolder: BaseViewHolder, item: MyRecord) {
        viewHolder.setText(R.id.tv_name, item.heroName)
                .setText(R.id.tv_level, "${item.recordLevel}级")
                .setText(R.id.tv_time, AppUtils.getTimeDetail(item.updateTime))
                .setImageResource(R.id.iv_logo, getHeroImg(item.heroName!!))
                .addOnClickListener(R.id.tv_delete)
                .addOnClickListener(R.id.cb_default)
                .addOnClickListener(R.id.tv_edit)

        val cbDefault = viewHolder.getView<CheckBox>(R.id.cb_default)
        cbDefault.isChecked = item.isDefault
    }
}
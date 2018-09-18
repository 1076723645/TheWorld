package com.smallcat.theworld.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R

/**
 * Created by smallCut on 2018/7/19.
 */
class AccessAdapter(data: MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_list_access, data) {

    override fun convert(viewHolder: BaseViewHolder, item: String) {
        viewHolder.setText(R.id.tv_name, item)
    }
}
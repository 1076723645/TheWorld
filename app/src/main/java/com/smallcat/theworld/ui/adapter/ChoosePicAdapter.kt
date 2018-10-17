package com.smallcat.theworld.ui.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R
import com.smallcat.theworld.model.bean.PicData

/**
 * Created by smallCut on 2018/7/19.
 */
class ChoosePicAdapter(data: MutableList<PicData>?) : BaseQuickAdapter<PicData, BaseViewHolder>(R.layout.item_skill, data) {

    override fun convert(viewHolder: BaseViewHolder, item: PicData) {

        val img = viewHolder.getView<ImageView>(R.id.iv_equip)
        if (item.imgUrl == 0){
            img.setImageResource(R.color.reg_choose_color)
        }else {
            img.setImageResource(item.imgUrl)
        }
        // Glide.with(mContext).load(R.drawable.wuqi_100).into(viewHolder.getView(R.id.iv_equip))

        val bg = viewHolder.getView<ImageView>(R.id.iv_bg)
        if (item.isSelected){
            bg.visibility = View.VISIBLE
        }else{
            bg.visibility = View.GONE
        }
    }
}
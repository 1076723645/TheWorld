package com.smallcat.theworld.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.smallcat.theworld.R
import com.smallcat.theworld.model.bean.RecordExpandChild
import com.smallcat.theworld.model.bean.RecordExpandData
import com.smallcat.theworld.utils.AppUtils

class RecordExpandAdapter(data: List<MultiItemEntity>) : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    companion object {
        const val TYPE_HEAD = 0
        const val TYPE_CONTENT = 1
    }

    init {
        addItemType(TYPE_HEAD, R.layout.item_expand_equip)
        addItemType(TYPE_CONTENT, R.layout.item_expand_child)
    }

    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        if (item == null){
            return
        }
        if (helper.itemViewType == TYPE_HEAD) {
            val headData = item as RecordExpandData
            helper.setText(R.id.tv_name, headData.equipName)
                    .addOnClickListener(R.id.tv_name)
                    .addOnClickListener(R.id.iv_img)
                    .addOnClickListener(R.id.tv_number)
            if (headData.equipIcon != 0) {
                helper.setImageResource(R.id.iv_img, headData.equipIcon)
            } else {
                helper.setImageResource(R.id.iv_img, R.color.color_cc)
            }
            val checkBox = helper.getView<CheckBox>(R.id.cb_expand)
            checkBox.isChecked = headData.isExpanded
            val tvNumber = helper.getView<TextView>(R.id.tv_number)
            if (headData.number > 0) {
                tvNumber.setTextColor(AppUtils.getResourcesColor(mContext, R.color.green))
            } else {
                tvNumber.setTextColor(AppUtils.getResourcesColor(mContext, R.color.red))
            }
            tvNumber.text = "(${headData.number}/1)"
            helper.itemView.setOnClickListener {
                val pos = helper.adapterPosition
                if (headData.isExpanded) {
                    collapse(pos)
                } else {
                    expand(pos)
                }
                checkBox.isChecked = !headData.isExpanded
            }
        } else {
            val childData = item as RecordExpandChild
            helper.setText(R.id.tv_name, childData.equipName)
                    .addOnClickListener(R.id.tv_name)
                    .addOnClickListener(R.id.iv_img)
            val tvNumber = helper.getView<TextView>(R.id.tv_number)
            if (childData.number > 0) {
                tvNumber.setTextColor(AppUtils.getResourcesColor(mContext, R.color.green))
            } else {
                tvNumber.setTextColor(AppUtils.getResourcesColor(mContext, R.color.red))
            }
            tvNumber.text = "(${childData.number}/1)"

            if (childData.equipIcon != 0) {
                helper.setImageResource(R.id.iv_img, childData.equipIcon)
            } else {
                helper.setImageResource(R.id.iv_img, R.color.color_cc)
            }

            val tvChoose = helper.getView<TextView>(R.id.tv_order)
            if (childData.isChoose) {
                tvChoose.visibility = View.VISIBLE
            } else {
                tvChoose.visibility = View.GONE

            }
        }
    }

}
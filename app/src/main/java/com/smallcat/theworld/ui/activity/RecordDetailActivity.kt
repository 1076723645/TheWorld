package com.smallcat.theworld.ui.activity

import android.content.Context
import android.content.Intent
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxActivity
import kotlinx.android.synthetic.main.normal_toolbar.*

class RecordDetailActivity : RxActivity() {

    companion object {
        private const val TAG = "RecordDetailActivity"
        fun getIntent(mContext: Context, id: Long) =
                Intent(mContext, RecordDetailActivity::class.java).apply {
                    putExtra(TAG, id)
                }
    }

    override val layoutId: Int
        get() = R.layout.activity_record_detail

    override fun initData() {
        tv_title.text = "存档详情"
    }



}

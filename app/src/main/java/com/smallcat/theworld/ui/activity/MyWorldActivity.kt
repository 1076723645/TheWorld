package com.smallcat.theworld.ui.activity

import android.content.Intent
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxActivity
import com.smallcat.theworld.model.bean.MsgEvent
import com.smallcat.theworld.model.callback.SureCallBack
import com.smallcat.theworld.model.db.MyRecord
import com.smallcat.theworld.ui.adapter.MyRecordAdapter
import com.smallcat.theworld.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_world.*
import kotlinx.android.synthetic.main.normal_toolbar.*
import org.litepal.crud.DataSupport


/**
 * 我的世界成长记录
 */
class MyWorldActivity : RxActivity() {

    private val list = ArrayList<MyRecord>()
    private lateinit var adapter: MyRecordAdapter

    private var mChoosePos = 0

    override val layoutId: Int
        get() = R.layout.activity_my_world

    override fun initData() {
        tv_title.text = "我的存档"
        adapter = MyRecordAdapter(list)
        adapter.emptyView = AppUtils.getEmptyView(mContext, "快去添加存档吧")
        adapter.setOnItemChildClickListener { _, view, position ->
            if (position < 0 || position >= list.size) {
                return@setOnItemChildClickListener
            }
            when (view.id) {
                R.id.tv_delete -> showSureDialog(position)
                R.id.cb_default -> changeDefaultChoose(position)
                R.id.tv_edit -> startActivity(RecordEditActivity.getIntent(mContext, list[position].id))
            }
        }
        adapter.setOnItemClickListener { _, _, position ->
            startActivity(RecordDetailActivity.getIntent(mContext, list[position].id))
        }
        rv_list.adapter = adapter
        fab.setOnClickListener {
            val intent = Intent(mContext, RecordAddActivity::class.java)
            CircularAnimUtil.startActivity(this@MyWorldActivity, intent, fab, R.drawable.zy_c8)
        }
        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        addSubscribe(Observable.create<List<MyRecord>> {
            val data = DataSupport.order("updateTime desc").find(MyRecord::class.java)
            it.onNext(data)

            val oldChooseId = sharedPref.chooseId
            for (i in data.indices) {
                if (oldChooseId == data[i].id) {
                    mChoosePos = i
                    break
                }
            }
            it.onComplete()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    showList(it)
                })
    }

    private fun showList(data: List<MyRecord>) {
        list.clear()
        list.addAll(data)
        adapter.setNewData(list)
    }

    private fun showSureDialog(position: Int) {
        showCheckDialog("确定删除存档吗", object : SureCallBack {
            override fun onSure() {
                deleteRecord(position)
            }
        })
    }

    /**
     * 删除默认存档需要重置mChoosePos
     */
    private fun deleteRecord(position: Int) {
        val data = list[position]
        //删除默认存档
        if (data.isDefault) {
            mContext.sharedPref.chooseId = -1L
            mChoosePos = 0
        }
        data.delete()
        list.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun changeDefaultChoose(position: Int) {
        val oldData = list[mChoosePos]
        oldData.isDefault = false
        adapter.notifyItemChanged(mChoosePos)
        oldData.save()

        val data = list[position]
        data.isDefault = true
        adapter.notifyItemChanged(position)
        data.save()

        sharedPref.chooseId = data.id
        mChoosePos = position
        RxBus.post(MsgEvent("", 12580))
    }
}
package com.smallcat.theworld.ui.activity

import android.content.Intent
import android.support.v7.app.AlertDialog
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxActivity
import com.smallcat.theworld.model.db.MyRecord
import com.smallcat.theworld.ui.adapter.MyRecordAdapter
import com.smallcat.theworld.utils.AppUtils
import com.smallcat.theworld.utils.CircularAnimUtil
import com.smallcat.theworld.utils.sharedPref
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_world.*
import kotlinx.android.synthetic.main.normal_toolbar.*
import org.litepal.crud.DataSupport
import android.util.TypedValue



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
            when(view.id){
                R.id.tv_delete -> showSureDialog(position)
                R.id.cb_default -> changeDefaultChoose(position)
                R.id.tv_edit -> startActivity(RecordEditActivity.getIntent(mContext, list[position].id))
            }
        }
        adapter.setOnItemClickListener { _, _, position ->
            startActivity(RecordEditActivity.getIntent(mContext, list[position].id))
        }
        rv_list.adapter = adapter
        refresh_layout.setColorSchemeResources(getColorPrimary())
        refresh_layout.setOnRefreshListener { loadData() }
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

    private fun getColorPrimary(): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
        return typedValue.resourceId
    }

    private fun loadData() {
        addSubscribe(Observable.create<List<MyRecord>> {
            val data = DataSupport.order("updateTime desc").find(MyRecord::class.java)
            it.onNext(data)

            val oldChooseId = sharedPref.chooseId
            for (i in data.indices){
                if (oldChooseId == data[i].id){
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
        refresh_layout.isRefreshing = false
        list.clear()
        list.addAll(data)
        adapter.setNewData(list)
    }

    private fun showSureDialog(position: Int){
        val dialog = AlertDialog.Builder(mContext)
                .setMessage("确定删除存档吗")
                .setPositiveButton("确定") { _, _ ->
                    val data = list[position]
                    data.delete()
                    list.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
                .setNegativeButton("取消",null)
                .create()
        dialog.show()
    }

    private fun changeDefaultChoose(position: Int){
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
    }
}
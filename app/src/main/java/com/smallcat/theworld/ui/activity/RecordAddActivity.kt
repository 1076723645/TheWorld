package com.smallcat.theworld.ui.activity

import android.app.Dialog
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxActivity
import com.smallcat.theworld.model.bean.ImgData
import com.smallcat.theworld.model.db.Hero
import com.smallcat.theworld.model.db.MyRecord
import com.smallcat.theworld.ui.adapter.ExclusiveAdapter
import com.smallcat.theworld.utils.LogUtil
import com.smallcat.theworld.utils.sharedPref
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.normal_toolbar.*
import org.litepal.crud.DataSupport
import java.util.ArrayList


class RecordAddActivity : RxActivity() {

    private val mData = ArrayList<ImgData>()
    private lateinit var adapter: ExclusiveAdapter

    override val layoutId: Int
        get() = R.layout.activity_record_add

    override fun initData() {
        tv_title.text = "选择职业"
        val recyclerView = findViewById<RecyclerView>(R.id.rv_profession)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        adapter = ExclusiveAdapter(mData)
        adapter.setOnItemClickListener { _, _, position ->
            showSureDialog(position)
        }
        recyclerView.adapter = adapter
        loadData()
    }

    private fun loadData() {
        addSubscribe(Observable.create<List<Hero>> {
            val list = DataSupport.findAll(Hero::class.java)
            it.onNext(list)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    showData(it)
                })
    }

    private fun showData(data: List<Hero>) {
        mData.clear()
        for (i in data.indices) {
            val imgData = ImgData()
            imgData.imgUrl = data[i].imgId
            imgData.name = data[i].heroName!!
            mData.add(imgData)
        }
        adapter.setNewData(mData)
    }

    private fun showSureDialog(position: Int) {
        val dialog = Dialog(mContext, R.style.CustomDialog)
        val view = LayoutInflater.from(mContext).inflate(R.layout.dialog_update, null)
        val tvMsg = view.findViewById<EditText>(R.id.tv_msg)
        val tvTitle = view.findViewById<TextView>(R.id.textView6)
        val tvSure = view.findViewById<TextView>(R.id.tv_download)
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        tvMsg.setText("确定创建存档吗")
        tvTitle.text = "提示"
        tvSure.setOnClickListener {
            dialog.dismiss()
            //创建一个基础的记录
            val data = MyRecord()
            data.heroName = mData[position].name
            data.heroImg = mData[position].imgUrl
            data.createTime = System.currentTimeMillis()
            data.updateTime = System.currentTimeMillis()
            data.recordLevel = 400
            data.save()
            //如果是第一个记录，就设为默认存档
            if (sharedPref.chooseId == -1L) {
                data.isDefault = true
                sharedPref.chooseId = data.id
            }
            startActivity(RecordEditActivity.getIntent(mContext, data.id))
            finish()
        }
        tvSure.text = "确定"
        tvCancel.text = "取消"
        tvCancel.setOnClickListener { dialog.dismiss() }
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
    }
}

package com.smallcat.theworld.ui.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxActivity
import com.smallcat.theworld.model.bean.MsgEvent
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.model.db.MyRecord
import com.smallcat.theworld.model.db.RecordThing
import com.smallcat.theworld.ui.adapter.RecordEquipAdapter
import com.smallcat.theworld.utils.AppUtils
import com.smallcat.theworld.utils.RxBus
import com.smallcat.theworld.utils.ToastUtil
import com.smallcat.theworld.utils.logE
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_record_edit.*
import kotlinx.android.synthetic.main.normal_toolbar.*
import org.litepal.crud.DataSupport


class RecordEditActivity : RxActivity() {

    private var recordId = 0L
    private lateinit var record: MyRecord
    private val tabTitles = arrayOf("武器", "头盔", "衣服", "饰品", "翅膀", "材料", "藏品", "其他")
    private val tabTitles1 = arrayOf("武器", "头盔", "衣服", "饰品", "翅膀")

    private var targetEquips: MutableList<RecordThing> = ArrayList()
    private lateinit var targetAdapter: RecordEquipAdapter

    private var wearEquips: MutableList<RecordThing> = ArrayList()
    private lateinit var wearAdapter: RecordEquipAdapter

    private var deleteList: MutableList<RecordThing> = ArrayList()

    companion object {
        private const val TAG = "RecordDetailActivity"
        fun getIntent(mContext: Context, id: Long) =
                Intent(mContext, RecordEditActivity::class.java).apply {
                    putExtra(TAG, id)
                }
    }

    override val layoutId: Int
        get() = R.layout.activity_record_edit

    override fun initData() {
        tv_right.text = "保存"
        tv_right.setOnClickListener {
            DataSupport.saveAll(targetEquips)
            DataSupport.saveAll(wearEquips)
            deleteList.forEach { i ->
                DataSupport.delete(RecordThing::class.java, i.id)
            }
            record.recordCode = et_record.text.toString()
            if (et_level.text.isNotEmpty()) {
                record.recordLevel = et_level.text.toString().toInt()
            }
            record.updateTime = System.currentTimeMillis()
            record.save()
            ToastUtil.shortShow("保存成功")
            RxBus.post(MsgEvent("", 12580))
        }
        tv_import.setOnClickListener {
            startActivityForResult(RecordImportActivity.getIntent(mContext, recordId), 1003)
        }
        tv_copy.setOnClickListener {
            // 得到剪贴板管理器
            val clipboard = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
            val clipData = ClipData.newPlainText(null, et_record.text.toString())
            // 把数据集设置（复制）到剪贴板
            clipboard.setPrimaryClip(clipData)
            ToastUtil.shortShow("复制成功")
        }
        recordId = intent.getLongExtra(TAG, 0)

        targetAdapter = RecordEquipAdapter(targetEquips)
        targetAdapter.addFooterView(getFooterView(mContext, 0))
        targetAdapter.setOnItemClickListener { _, _, position ->
            if (position < 0 || position >= targetEquips.size) {
                return@setOnItemClickListener
            }
            AppUtils.goEquipDetailActivity(mContext, targetEquips[position].equipName)
        }
        targetAdapter.setOnItemChildClickListener { _, _, position ->
            if (position < 0 || position >= targetEquips.size) {
                return@setOnItemChildClickListener
            }
            if (targetEquips[position].number == 1) {
                deleteList.add(targetEquips[position])
                targetEquips.removeAt(position)
                targetAdapter.notifyItemRemoved(position)
            } else {
                targetEquips[position].number--
                targetAdapter.notifyItemChanged(position)
            }
        }
        val layoutManager = GridLayoutManager(mContext, 5)
        rv_target.layoutManager = layoutManager
        rv_target.isNestedScrollingEnabled = false
        rv_target.adapter = targetAdapter

        wearAdapter = RecordEquipAdapter(wearEquips)
        wearAdapter.addFooterView(getFooterView(mContext, 1))
        wearAdapter.setOnItemClickListener { _, _, position ->
            if (position < 0 || position >= wearEquips.size) {
                return@setOnItemClickListener
            }
            AppUtils.goEquipDetailActivity(mContext, wearEquips[position].equipName)
        }
        wearAdapter.setOnItemChildClickListener { _, _, position ->
            if (position < 0 || position >= wearEquips.size) {
                return@setOnItemChildClickListener
            }
            if (wearEquips[position].number == 1) {
                deleteList.add(wearEquips[position])
                wearEquips.removeAt(position)
                wearAdapter.notifyItemRemoved(position)
            } else {
                wearEquips[position].number--
                wearAdapter.notifyItemChanged(position)
            }
        }
        val layout1 = GridLayoutManager(mContext, 5)
        rv_have.layoutManager = layout1
        rv_have.isNestedScrollingEnabled = false
        rv_have.adapter = wearAdapter

        loadData()
    }

    private fun getFooterView(mContext: Context, type: Int): View {
        val view = LayoutInflater.from(mContext).inflate(R.layout.footer_view, null)
        val ivAdd = view.findViewById<ImageView>(R.id.iv_img)
        ivAdd.setOnClickListener {
            showChooseDialog(type)
        }
        return view
    }

    /**
     *  设置dialog的宽度为屏幕的80%解决部分手机超出屏幕的问题
     */
    private fun showChooseDialog(type: Int) {
        val requestCode: Int
        val array: Array<String>
        if (type == 0) {
            array = tabTitles1
            requestCode = 1001
        } else {
            array = tabTitles
            requestCode = 1002
        }
        val builder = AlertDialog.Builder(mContext)
        builder.setItems(array) { dialog, which ->
            dialog.dismiss()
            startActivityForResult(EquipAddActivity.getIntent(mContext, which), requestCode)
        }
        val dialog = builder.create()
        val lp = dialog.window?.attributes
        val dm = resources.displayMetrics
        lp?.width = dm.widthPixels * 0.6.toInt()
        dialog.window?.attributes = lp //设置宽度
        dialog.show()
    }

    private fun loadData() {
        showLoading()
        addSubscribe(Observable.create<List<MyRecord>> {
            val data = DataSupport.where("id = ?", recordId.toString()).find(MyRecord::class.java)
            it.onNext(data)
            it.onComplete()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { dismissLoading() }
                .subscribe {
                    if (it.isNotEmpty()) {
                        record = it[0]
                        showData()
                    }
                })
    }

    private fun showData() {
        tv_title.text = record.heroName
        tv_create_time.text = AppUtils.getTimeDetail(record.createTime)
        tv_update_time.text = AppUtils.getTimeDetail(record.updateTime)
        et_level.setText("${record.recordLevel}")
        et_record.setText(record.recordCode)
        targetEquips = DataSupport.where("recordId = ? and type = ?", recordId.toString(), "1")
                .order("partId")
                .find(RecordThing::class.java)
        wearEquips = DataSupport.where("recordId = ? and type = ?", recordId.toString(), "2")
                .order("partId")
                .find(RecordThing::class.java)
        showList()
    }

    private fun showList() {
        targetAdapter.setNewData(targetEquips)
        wearAdapter.setNewData(wearEquips)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 1003) {
            val recordThings = data.getParcelableArrayListExtra<RecordThing>("data")
            if (recordThings.isNotEmpty()) {
                recordThings[0].toString().logE()
            }
            for (record in recordThings){
                var isHave = false
                for (j in wearEquips){
                    if (record.equipName == j.equipName){
                        j.number++
                        isHave = true
                        break
                    }
                }
                if (!isHave) {
                    wearEquips.add(record)
                }
            }
            wearAdapter.setNewData(wearEquips)
            val level = data.getStringExtra("level")
            val code = data.getStringExtra("code")
            et_level.setText(level)
            et_record.setText(code)
            return
        }
        val equipData = Gson().fromJson(data.getStringExtra("data"), Equip::class.java)
        /**
         * 添加装备时先遍历列表有没有该装备，有就加数量1，没有就新加一个bean
         */
        val recordThing = RecordThing()
        recordThing.recordId = recordId
        recordThing.number = 1
        recordThing.equipName = equipData.equipName
        recordThing.equipImg = equipData.imgId
        recordThing.part = equipData.type
        recordThing.partId = equipData.typeId
        when (requestCode) {
            1001 -> {
                for (i in targetEquips.indices) {
                    if (targetEquips[i].equipName == equipData.equipName) {
                        targetEquips[i].number++
                        targetAdapter.notifyItemChanged(i)
                        return
                    }
                }
                recordThing.type = 1
                targetEquips.add(recordThing)
                targetAdapter.setNewData(targetEquips)
            }
            1002 -> {
                for (i in wearEquips.indices) {
                    if (wearEquips[i].equipName == equipData.equipName) {
                        wearEquips[i].number++
                        wearAdapter.notifyItemChanged(i)
                        return
                    }
                }
                recordThing.type = 2
                wearEquips.add(recordThing)
                wearAdapter.setNewData(wearEquips)
            }
        }
    }

}

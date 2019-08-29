package com.smallcat.theworld.ui.activity

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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

    //当前物品的集合
    private val dataList: MutableList<String> = ArrayList()

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
            if (et_level.text.isNotEmpty()){
                record.recordLevel = et_level.text.toString().toInt()
            }
            record.updateTime = System.currentTimeMillis()
            record.save()
            ToastUtil.shortShow("保存成功")
            RxBus.post(MsgEvent("", 12580))
        }
        tv_build.setOnClickListener {
            buildThing()
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
            if (position < 0 || position >= targetEquips.size){
                return@setOnItemClickListener
            }
            AppUtils.goEquipDetailActivity(mContext, targetEquips[position].equipName!!)
        }
        targetAdapter.setOnItemChildClickListener { _, _, position ->
            if (position < 0 || position >= targetEquips.size){
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
            if (position < 0 || position >= wearEquips.size){
                return@setOnItemClickListener
            }
            AppUtils.goEquipDetailActivity(mContext, wearEquips[position].equipName!!)
        }
        wearAdapter.setOnItemChildClickListener { _, _, position ->
            if (position < 0 || position >= wearEquips.size){
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
        val lp = dialog?.window?.attributes
        val dm = resources.displayMetrics
        lp?.width = dm.widthPixels * 0.6.toInt()
        dialog?.window?.attributes = lp //设置宽度
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

    private fun buildThing() {
        if (wearEquips.isEmpty()) {
            ToastUtil.shortShow("没有物品")
            return
        }
        //进阶列表
        val advanceList = ArrayList<Equip>()
        //可以合成的列表
        val finalList: MutableList<Equip> = ArrayList()

        showLoading()
        dataList.clear()
        addSubscribe(Observable.create<List<Equip>> {
            //获取当前物品的所有进阶物品
            for (k in wearEquips.indices) {

                val equipName = wearEquips[k].equipName!!
                dataList.add(equipName)
                //所有可进阶列表
                val equipList = DataSupport.where("access like ?", "%$equipName%").find(Equip::class.java)
                for (i in 0 until equipList.size) {
                    val mEquip = equipList[i]
                    val mList = mEquip.dataList
                    for (j in 0 until mList.size) {
                        if (mList[j] == equipName) {
                            if (!AppUtils.checkContain(advanceList, mEquip)) {
                                advanceList.add(mEquip)
                                break
                            }
                        } else if (mList[j].contains("/")) {
                            val choose1 = mList[j].substring(0, mList[j].indexOf('/'))
                            val choose2 = mList[j].substring(mList[j].indexOf('/') + 1)
                            if (equipName == choose1 || equipName == choose2) {
                                if (!AppUtils.checkContain(advanceList, mEquip)) {
                                    advanceList.add(mEquip)
                                    break
                                }
                            }
                        }
                    }
                }
            }

            for (i in advanceList) {
                //当前物品列表是否包含合成列表
                val mList = i.dataList.toMutableList()
                if (dataList.containsAll(mList)) {
                    finalList.add(i)
                    continue
                }
                //判断2选1合成的情况
                for (j in mList.indices) {
                    if (mList[j].contains("/")) {
                        val choose1 = mList[j].substring(0, mList[j].indexOf('/'))
                        val choose2 = mList[j].substring(mList[j].indexOf('/') + 1)
                        val newList1: MutableList<String> = ArrayList()
                        val newList2: MutableList<String> = ArrayList()
                        newList1.addAll(mList)
                        newList1.removeAt(j)
                        newList1.add(j, choose1)
                        newList2.addAll(mList)
                        newList2.removeAt(j)
                        newList2.add(j, choose2)
                        if (dataList.containsAll(newList1)) {
                            finalList.add(i)
                            break
                        }
                        if (dataList.containsAll(newList2)) {
                            finalList.add(i)
                            break
                        }
                    }
                }
            }
            it.onNext(finalList)
            it.onComplete()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { dismissLoading() }
                .subscribe {
                    showBuildList(it)
                })
    }

    private fun showBuildList(data: List<Equip>) {
        if (data.isEmpty()) {
            ToastUtil.shortShow("没有可以合成的装备")
            return
        }
        val array = Array(data.size) { "" }
        for (i in data.indices) {
            array[i] = data[i].equipName
        }
        val builder = AlertDialog.Builder(mContext)
        builder.setItems(array) { dialog, which ->
            dialog.dismiss()
            buildSuccess(data[which])
        }
        val dialog = builder.create()
        val lp = dialog?.window?.attributes
        val dm = resources.displayMetrics
        lp?.width = dm.widthPixels * 0.8.toInt()
        dialog?.window?.attributes = lp //设置宽度
        dialog.show()
    }

    /**
     * 合成之后添加合成物品，删除合成材料
     */
    private fun buildSuccess(equip: Equip) {
        val mList = equip.dataList
        for (i in mList.indices) {
            if (mList[i].contains("/")) {
                val choose1 = mList[i].substring(0, mList[i].indexOf('/'))
                val choose2 = mList[i].substring(mList[i].indexOf('/') + 1)
                if (dataList.contains(choose1) && dataList.contains(choose2)) {
                    val dialog = Dialog(mContext, R.style.CustomDialog)
                    val v1 = LayoutInflater.from(mContext).inflate(R.layout.dialog_choose, null)
                    dialog.setContentView(v1)
                    dialog.setCanceledOnTouchOutside(true)
                    dialog.setCancelable(true)
                    val tvName1 = v1.findViewById<TextView>(R.id.tv_name)
                    val tvName2 = v1.findViewById<TextView>(R.id.tv_name2)
                    tvName1.text = choose1
                    tvName2.text = choose2
                    tvName1.setOnClickListener {
                        addDeleteThing(equip, choose1)
                        dialog.dismiss()
                    }
                    tvName2.setOnClickListener {
                        addDeleteThing(equip, choose2)
                        dialog.dismiss()
                    }
                    dialog.show()
                    return
                }
                break
            }
        }
        addDeleteThing(equip, "")
    }

    private fun addDeleteThing(equip: Equip, name: String) {
        val recordThing = RecordThing()
        recordThing.equipName = equip.equipName
        recordThing.number = 1
        recordThing.equipImg = equip.imgId
        recordThing.type = 2
        recordThing.recordId = recordId
        val mList = equip.dataList.toMutableList()
        if (name == "") {
            for (j in mList.indices) {
                if (mList[j].contains("/")) {
                    val choose1 = mList[j].substring(0, mList[j].indexOf('/'))
                    val choose2 = mList[j].substring(mList[j].indexOf('/') + 1)
                    mList.removeAt(j)
                    mList.add(choose1)
                    mList.add(choose2)
                    break
                }
            }
        } else {
            mList.add(name)
        }
        for (i in wearEquips.size - 1 downTo 0) {
            for (j in mList) {
                if (wearEquips[i].equipName == j) {
                    deleteList.add(wearEquips[i])
                    wearEquips.removeAt(i)
                    break
                }
            }
        }
        for (i in wearEquips.indices) {
            if (wearEquips[i].equipName == equip.equipName) {
                wearEquips[i].number++
                wearAdapter.setNewData(wearEquips)
                return
            }
        }
        wearEquips.add(0, recordThing)
        wearAdapter.setNewData(wearEquips)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
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

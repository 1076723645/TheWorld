package com.smallcat.theworld.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxActivity
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.model.db.RecordThing
import com.smallcat.theworld.utils.*
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_record_import.*
import org.litepal.crud.DataSupport
import java.io.File
import java.io.Serializable


class RecordImportActivity : RxActivity() {

    private var recordId = 0L
    private var recordString = ""
    private var readErrorCount = 0
    private var wearEquips = ArrayList<RecordThing>()
    private var recordLevel = ""
    private var recordCode = ""

    companion object {
        private const val TAG = "RecordImportActivity"
        fun getIntent(mContext: Context, id: Long) =
                Intent(mContext, RecordImportActivity::class.java).apply {
                    putExtra(TAG, id)
                }
    }

    override val layoutId: Int
        get() = R.layout.activity_record_import

    override fun initData() {
        recordId = intent.getLongExtra(TAG, 0)
        tv_choose.setOnClickListener { getDoc() }
        et_record.setText(recordString)
        tv_import.setOnClickListener { import() }
    }

    private fun getDoc() {
        FilePickerBuilder.instance
                .setMaxCount(1)
                .setActivityTheme(R.style.LibAppTheme)
                .pickFile(this)
    }

    private fun import() {
        recordString = et_record.text.toString()
        if (recordString == "") {
            "请输入存档".toast()
            return
        }
        showLoading()
        addSubscribe(Observable.create<String> {
            it.onNext("读取存档内容")
            analysisRecord()
            it.onComplete()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { dismissLoading() }
                .subscribe({
                    it.toast()
                }, {
                    "存档内容错误或者存档版本不兼容".toast()
                }, {
                    "${readErrorCount}个装备读取失败".toast()
                    val intent = Intent()
                    intent.putParcelableArrayListExtra("data", wearEquips)
                    intent.putExtra("level", recordLevel)
                    intent.putExtra("code", recordCode)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }))
    }

    private fun analysisRecord() {
        readErrorCount = 0
        val dataList = recordString.split("call Preload")
        var thingStart = 0
        var thingEnd = 0
        for (i in dataList.indices) {
            //获取等级信息
            val data = dataList[i]
            if (data.contains("等级: ")) {
                val end = data.indexOf("\" )")
                val start = data.indexOf("等级: ")
                if (end != -1) {
                    recordLevel = data.substring(start + 4, end)
                }
            }
            //获取物品区间
            if (data.contains("-携带物品-")) {
                thingStart = i + 1
            } else if (data.contains("-背包-") && thingStart == 0) {
                thingStart = i + 1
            } else if (data.contains("存档代码") && thingEnd == 0) {
                thingEnd = i
            }
            //获取存档代码
            if (data.contains("存档代码")) {
                val end = data.indexOf("\" )")
                val start = data.indexOf("存档代码")
                if (end != -1) {
                    recordCode = if (recordCode == "") {
                        data.substring(start + 8, end)
                    } else {
                        recordCode + "\n" + data.substring(start + 8, end)
                    }
                }
            }
        }
        for (i in thingStart..thingEnd) {
            val s = dataList[i]
            val end = s.indexOf("\" )")
            val start = s.indexOf(". ")
            if (start != -1 && end != -1) {
                val equipName = s.substring(start + 2, end)
                val equips = DataSupport.where("equipName = ?", equipName).find(Equip::class.java)
                if (equips.isNotEmpty()) {
                    var needAdd = true
                    val equipData = equips[0]
                    val recordThing = RecordThing()
                    recordThing.recordId = recordId
                    recordThing.number = 1
                    recordThing.equipName = equipData.equipName
                    recordThing.equipImg = equipData.imgId
                    recordThing.part = equipData.type
                    recordThing.partId = equipData.typeId
                    recordThing.type = 2
                    for (j in wearEquips.indices) {
                        if (wearEquips[j].equipName == equipData.equipName) {
                            wearEquips[j].number++
                            needAdd = false
                            break
                        }
                    }
                    if (needAdd) {
                        wearEquips.add(recordThing)
                    }
                } else {
                    readErrorCount++
                    equipName.logE()
                }
            }
        }
        for (i in wearEquips){
            "导入的装备${i.equipName}".logE()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FilePickerConst.REQUEST_CODE_DOC) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val list = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS)
                list?.let {
                    LogUtil.e(TAG, list.size.toString())
                    et_record.setText(list[0])
                    val file = File(list[0])
                    if (file.exists()) {
                        recordString = FileUtils.getFileContent(file)
                        et_record.setText(recordString)
                    }
                }
            }
        }
    }

}

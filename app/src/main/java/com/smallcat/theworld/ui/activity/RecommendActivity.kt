package com.smallcat.theworld.ui.activity

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxActivity
import com.smallcat.theworld.model.bean.MsgEvent
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.model.db.EquipRecommend
import com.smallcat.theworld.model.db.RecordThing
import com.smallcat.theworld.ui.adapter.EquipShowAdapter
import com.smallcat.theworld.utils.RxBus
import com.smallcat.theworld.utils.sharedPref
import com.smallcat.theworld.utils.toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_recommend.*
import kotlinx.android.synthetic.main.normal_toolbar.*
import org.litepal.crud.DataSupport

class RecommendActivity : RxActivity() {

    private lateinit var recommendData: EquipRecommend
    private lateinit var adapterEarly: EquipShowAdapter
    private val listEarly = ArrayList<Equip>()
    private lateinit var adapterFinal: EquipShowAdapter
    private val listFinal = ArrayList<Equip>()

    private lateinit var targetEquips: MutableList<RecordThing>
    private var recordId = -1L

    companion object {
        private const val TAG = "RecommendActivity"
        fun getIntent(mContext: Context, id: Long) =
                Intent(mContext, RecommendActivity::class.java).apply {
                    putExtra(TAG, id)
                }
    }

    override val layoutId: Int
        get() = R.layout.activity_recommend

    override fun initData() {
        val recommendId = intent.getLongExtra(TAG, 0)
        val list = DataSupport.where("id = ?", recommendId.toString()).find(EquipRecommend::class.java)
        recommendData = list[0]
        recordId = sharedPref.chooseId
        targetEquips = DataSupport.where("recordId = ? and type = ?", recordId.toString(), "1").find(RecordThing::class.java)
        tv_use1.setOnClickListener {
            if (recordId == -1L) {
                "请先选择默认存档".toast()
                return@setOnClickListener
            }
            addToTargetEquip(listEarly)
        }
        tv_use2.setOnClickListener {
            if (recordId == -1L) {
                "请先选择默认存档".toast()
                return@setOnClickListener
            }
            addToTargetEquip(listFinal)
        }
        tv_title.text = recommendData.title
        tv_early.text = recommendData.resonEarly
        tv_final.text = recommendData.resonFinal
        tv_action.text = recommendData.action

        adapterEarly = EquipShowAdapter(listEarly)
        adapterEarly.setOnItemClickListener { _, _, position ->
            Intent(mContext, EquipDetailActivity::class.java).apply {
                putExtra("id", listEarly[position].id.toString())
                startActivity(this)
            }
        }
        val layout1 = GridLayoutManager(mContext, 5)
        rv_early.layoutManager = layout1
        rv_early.adapter = adapterEarly
        rv_early.isNestedScrollingEnabled = false

        adapterFinal = EquipShowAdapter(listFinal)
        adapterFinal.setOnItemClickListener { _, _, position ->
            Intent(mContext, EquipDetailActivity::class.java).apply {
                putExtra("id", listFinal[position].id.toString())
                startActivity(this)
            }
        }
        val layout = GridLayoutManager(mContext, 5)
        rv_final.layoutManager = layout
        rv_final.adapter = adapterFinal
        rv_final.isNestedScrollingEnabled = false

        loadData()
    }

    private fun loadData() {
        showLoading()
        addSubscribe(Observable.create<String> {
            for (i in recommendData.equipEarly) {
                val data = DataSupport.where("equipName = ?", i).find(Equip::class.java)
                listEarly.addAll(data)
            }
            for (i in recommendData.equipFinal) {
                val data = DataSupport.where("equipName = ?", i).find(Equip::class.java)
                listFinal.addAll(data)
            }
            it.onNext("")
            it.onComplete()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { dismissLoading() }
                .subscribe {
                    if (it.isNotEmpty()) {
                        showData()
                    }
                })
    }

    private fun showData() {
        adapterFinal.setNewData(listFinal)
        adapterEarly.setNewData(listEarly)
    }

    private fun addToTargetEquip(list: List<Equip>) {
        for (equip in list) {
            var isAdd = false
            //先判断物品是否已经被加到目标物品中
            for (i in targetEquips){
                if (i.equipName == equip.equipName){
                    isAdd = true
                    break
                }
            }
            if (isAdd){
                continue
            }
            val recordThing = RecordThing()
            recordThing.equipName = equip.equipName
            recordThing.number = 1
            recordThing.equipImg = equip.imgId
            recordThing.type = 1
            recordThing.recordId = recordId
            recordThing.part = equip.type
            recordThing.partId = equip.typeId
            recordThing.save()
            targetEquips.add(recordThing)
        }
        "添加成功".toast()
        RxBus.post(MsgEvent("msg", 1))
    }

}

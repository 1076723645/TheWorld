package com.smallcat.theworld.ui.fragment


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxFragment
import com.smallcat.theworld.model.bean.MsgEvent
import com.smallcat.theworld.model.bean.RecordExpandChild
import com.smallcat.theworld.model.bean.RecordExpandData
import com.smallcat.theworld.model.callback.SureCallBack
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.model.db.MyRecord
import com.smallcat.theworld.model.db.RecordThing
import com.smallcat.theworld.ui.activity.EquipDetailActivity
import com.smallcat.theworld.ui.activity.MyWorldActivity
import com.smallcat.theworld.ui.adapter.RecordEquipShowAdapter
import com.smallcat.theworld.ui.adapter.RecordExpandAdapter
import com.smallcat.theworld.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_record.*
import org.litepal.crud.DataSupport


/**
 * @author smallCut
 * @date 20190527
 */
class RecordFragment : RxFragment() {

    private lateinit var targetEquips: MutableList<RecordThing>
    private lateinit var adapter: RecordExpandAdapter

    private lateinit var wearAdapter: RecordEquipShowAdapter
    private var wearEquips = ArrayList<RecordThing>()

    private var deleteList: MutableList<RecordThing> = ArrayList()

    private val list = ArrayList<MultiItemEntity>()
    private var recordId = 0L
    private var record = MyRecord()
    private var isShow = true

    override val layoutId: Int
        get() = R.layout.fragment_record

    override fun initView() {
        fab_edit.setOnClickListener {
            val intent = Intent(mContext, MyWorldActivity::class.java)
            startActivity(intent)
        }
        swipe_refresh.setOnRefreshListener { loadData() }
        swipe_refresh.setColorSchemeResources(getColorPrimary())
        tv_hide.setOnClickListener { showHideWearList() }
        recordId = mContext.sharedPref.chooseId
        adapter = RecordExpandAdapter(list)
        adapter.setOnItemClickListener { _, _, position ->
            if (list[position].itemType == RecordExpandAdapter.TYPE_CONTENT) {
                showSureDialog(position)
            }
        }
        adapter.setOnItemChildClickListener { _, view, position ->
            if (position >= list.size) {
                return@setOnItemChildClickListener
            }
            if (list[position].itemType == RecordExpandAdapter.TYPE_CONTENT) {
                //子列表跳转装备详情
                val data = list[position] as RecordExpandChild
                val list = DataSupport.where("equipName = ?", data.equipName).find(Equip::class.java)
                if (list.isNotEmpty()) {
                    Intent(mContext, EquipDetailActivity::class.java).apply {
                        putExtra("id", list[0].id.toString())
                        startActivity(this)
                    }
                }
            } else {
                //父列表点击名字，数量合成，图片跳转详情
                if (view.id == R.id.iv_img) {
                    val data = list[position] as RecordExpandData
                    val list = DataSupport.where("equipName = ?", data.equipName).find(Equip::class.java)
                    if (list.size > 0) {
                        Intent(mContext, EquipDetailActivity::class.java).apply {
                            putExtra("id", list[0].id.toString())
                            startActivity(this)
                        }
                    }
                } else {
                    canBuild(position)
                }
            }
        }
        rv_target.isNestedScrollingEnabled = false
        rv_target.adapter = adapter

        wearAdapter = RecordEquipShowAdapter(wearEquips)
        //wearAdapter.addFooterView(getFooterView(mContext, 1))
        wearAdapter.setOnItemClickListener { _, _, position ->
            if (position >= wearEquips.size) {
                return@setOnItemClickListener
            }
            val list = DataSupport.where("equipName = ?", wearEquips[position].equipName).find(Equip::class.java)
            if (list.isNotEmpty()) {
                Intent(mContext, EquipDetailActivity::class.java).apply {
                    putExtra("id", list[0].id.toString())
                    startActivity(this)
                }
            }
        }
        val layout1 = GridLayoutManager(mContext, 6)
        rv_have.layoutManager = layout1
        rv_have.isNestedScrollingEnabled = false
        rv_have.adapter = wearAdapter

        registerEvent()
        loadData()
    }

    private fun getColorPrimary(): Int {
        val typedValue = TypedValue()
        mContext.theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
        return typedValue.resourceId
    }

    private fun loadData() {
        wearEquips.clear()
        list.clear()
        addSubscribe(Observable.create<String> {
            recordId = mContext.sharedPref.chooseId
            targetEquips = DataSupport.where("recordId = ? and type = ?", recordId.toString(), "1")
                    .order("partId")
                    .find(RecordThing::class.java)
            wearEquips.addAll(DataSupport.where("recordId = ? and type = ?", recordId.toString(), "2")
                    .order("partId")
                    .find(RecordThing::class.java))
            it.onNext("wear")
            val data = DataSupport.where("id = ?", recordId.toString()).find(MyRecord::class.java)
            if (data.size > 0) {
                record = data[0]
            }
            createData()
            it.onNext("")
            it.onComplete()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { swipe_refresh.isRefreshing = false }
                .subscribe {
                    if (it == "wear") {
                        wearAdapter.notifyDataSetChanged()
                    } else {
                        adapter.notifyDataSetChanged()
                    }
                })
    }

    private fun createData() {
        for (data in targetEquips) {
            data.toString().logD()
            val newData = RecordExpandData()
            newData.equipName = data.equipName!!
            newData.equipIcon = data.equipImg
            val equip = DataSupport.where("equipName = ?", newData.equipName).find(Equip::class.java)[0]
            newData.dataList = equip.dataList as ArrayList<String>
            if (newData.dataList.size <= 1) {
                for (k in wearEquips.indices) {
                    if (wearEquips[k].equipName == newData.equipName) {
                        newData.number = wearEquips[k].number
                        break
                    }
                }
                list.add(newData)
                continue
            }
            for (name in newData.dataList) {
                val nameHave = DataSupport.where("equipName = ?", name).find(Equip::class.java)
                when {
                    name.contains("/") -> {
                        /**
                         * 2选1的情况
                         */
                        val choose1 = name.substring(0, name.indexOf('/'))
                        val choose2 = name.substring(name.indexOf('/') + 1)
                        val child1 = RecordExpandChild()
                        val child2 = RecordExpandChild()
                        val child1Equip = DataSupport.where("equipName = ?", choose1).find(Equip::class.java)[0]
                        val child2Equip = DataSupport.where("equipName = ?", choose2).find(Equip::class.java)[0]
                        child1.equipName = child1Equip.equipName
                        child1.equipIcon = child1Equip.imgId
                        child2.equipName = child2Equip.equipName
                        child2.equipIcon = child2Equip.imgId
                        child1.isChoose = true
                        child2.isChoose = true
                        for (k in wearEquips.indices) {
                            if (wearEquips[k].equipName == newData.equipName) {
                                newData.number = wearEquips[k].number
                            }
                            if (wearEquips[k].equipName == child1.equipName) {
                                child1.number = wearEquips[k].number
                            }
                            if (wearEquips[k].equipName == child2.equipName) {
                                child2.number = wearEquips[k].number
                            }
                        }
                        newData.addSubItem(child1)
                        newData.addSubItem(child2)
                    }
                    nameHave.isEmpty() -> {
                        /**
                         * 合成中包含粉末,魔法石,矿物等可能搜索不到，需要单独处理
                         */
                        val child = RecordExpandChild()
                        child.equipName = name
                        child.number = 1
                        for (k in wearEquips.indices) {
                            if (wearEquips[k].equipName == newData.equipName) {
                                newData.number = wearEquips[k].number
                            }
                        }
                        newData.addSubItem(child)
                    }
                    else -> {
                        val child = RecordExpandChild()
                        val childEquip = nameHave[0]
                        child.equipName = childEquip.equipName
                        child.equipIcon = childEquip.imgId
                        for (k in wearEquips.indices) {
                            if (wearEquips[k].equipName == newData.equipName) {
                                newData.number = wearEquips[k].number
                            }
                            if (wearEquips[k].equipName == child.equipName) {
                                child.number = wearEquips[k].number
                            }
                        }
                        newData.addSubItem(child)
                    }
                }
            }
            list.add(newData)
        }
    }

    private fun showSureDialog(position: Int) {
        mContext.showCheckDialog(mActivity.supportFragmentManager, "确定添加物品吗", object : SureCallBack {
            override fun onSure() {
                val data = list[position] as RecordExpandChild
                data.number++
                adapter.setNewData(list)
                val name = data.equipName
                val nameHave = DataSupport.where("equipName = ?", name).find(Equip::class.java)
                if (nameHave.isEmpty()) {
                    return
                }
                val recordThing = RecordThing()
                recordThing.recordId = recordId
                recordThing.number = 1
                recordThing.equipName = data.equipName
                recordThing.equipImg = data.equipIcon
                recordThing.part = nameHave[0].type
                recordThing.partId = nameHave[0].typeId
                for (i in wearEquips.indices) {
                    if (wearEquips[i].equipName == data.equipName) {
                        wearEquips[i].number++
                        wearAdapter.notifyItemChanged(i)
                        saveData()
                        return
                    }
                }
                recordThing.type = 2
                wearEquips.add(recordThing)
                wearAdapter.notifyItemInserted(wearEquips.size)
                saveData()
            }
        })
    }

    /**
     * 检查合成物品是否全部都有
     * 或的情况需要单独判断(将或的物品直接相加总和大于0就是可以合成)
     */
    private fun canBuild(position: Int) {
        val data = list[position] as RecordExpandData
        /**
         * 直接掉落的物品没有展开项
         */
        if (!data.hasSubItem()) {
            showBuildDialog(position, 0)
            return
        }
        var chooseNumber = 0
        var hasChoose = false
        for (i in data.subItems.indices) {
            val bean = data.subItems[i]
            if (bean.isChoose) {
                hasChoose = true
                chooseNumber += bean.number
            } else if (bean.number < 1) {
                "合成物品不足".toast()
                return
            }
        }
        if (chooseNumber == 0 && hasChoose) {
            "合成物品不足".toast()
            return
        }
        showBuildDialog(position, chooseNumber)
    }

    private fun showBuildDialog(pos: Int, chooseNumber: Int) {
        mContext.showCheckDialog(mActivity.supportFragmentManager, "确定合成物品吗", object : SureCallBack {
            override fun onSure() {
                buildSuccess(pos, chooseNumber)
            }
        })
    }

    /**
     * 合成之后添加合成物品，删除合成材料
     */
    private fun buildSuccess(pos: Int, chooseNumber: Int) {
        val equip = list[pos] as RecordExpandData
        val mList = equip.dataList
        for (i in mList.indices) {
            if (mList[i].contains("/")) {
                val choose1 = mList[i].substring(0, mList[i].indexOf('/'))
                val choose2 = mList[i].substring(mList[i].indexOf('/') + 1)
                if (chooseNumber == 2) {
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
                        addDeleteThing(pos, choose1)
                        dialog.dismiss()
                    }
                    tvName2.setOnClickListener {
                        addDeleteThing(pos, choose2)
                        dialog.dismiss()
                    }
                    dialog.show()
                    return
                }
                break
            }
        }
        addDeleteThing(pos, "")
    }

    private fun addDeleteThing(pos: Int, name: String) {
        val equip = list[pos] as RecordExpandData
        equip.number++
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
                    if (wearEquips[i].number > 1) {
                        wearEquips[i].number--
                        wearAdapter.notifyItemChanged(i)
                    } else {
                        deleteList.add(wearEquips[i])
                        wearEquips.removeAt(i)
                        wearAdapter.notifyItemRemoved(i)
                    }
                    break
                }
            }
        }
        if (equip.hasSubItem()) {
            for (data in equip.subItems) {
                data.number--
            }
        }
        for (i in wearEquips.indices) {
            if (wearEquips[i].equipName == equip.equipName) {
                wearEquips[i].number++
                wearAdapter.notifyItemChanged(i)
                if (equip.hasSubItem()) {
                    adapter.notifyItemRangeChanged(pos, equip.subItems.size + 1)
                } else {
                    adapter.notifyItemChanged(pos)
                }
                saveData()
                return
            }
        }
        val recordThing = RecordThing()
        recordThing.equipName = equip.equipName
        recordThing.number = 1
        recordThing.equipImg = equip.equipIcon
        recordThing.type = 2
        recordThing.recordId = recordId
        wearEquips.add(recordThing)
        wearAdapter.notifyItemInserted(wearEquips.size)
        if (equip.hasSubItem()) {
            adapter.notifyItemRangeChanged(pos,  equip.subItems.size + 1)
        } else {
            adapter.notifyItemChanged(pos)
        }
        saveData()
    }

    private fun saveData() {
        DataSupport.saveAll(wearEquips)
        for (i in deleteList) {
            DataSupport.delete(RecordThing::class.java, i.id)
        }
        deleteList.clear()
        record.updateTime = System.currentTimeMillis()
        record.save()
    }

    private fun registerEvent() {
        addSubscribe(RxBus.toObservable(MsgEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.type == 12580) {
                        loadData()
                    } else if (it.type == 1) {
                        loadData()
                    }
                })
    }

    private fun showHideWearList() {
        isShow = !isShow
        if (isShow) {
            tv_hide.text = "隐藏"
            rv_have.visibility = View.VISIBLE
        } else {
            tv_hide.text = "显示"
            rv_have.visibility = View.GONE
        }
    }

}

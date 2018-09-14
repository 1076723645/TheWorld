package com.smallcat.theworld.ui.activity

import android.annotation.SuppressLint
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseActivity
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.ui.adapter.AccessAdapter
import com.smallcat.theworld.utils.AppUtils
import org.litepal.crud.DataSupport
import java.util.*

class EquipDetailActivity : BaseActivity() {

    @BindView(R.id.iv_equip)
    lateinit var ivEquip: ImageView
    @BindView(R.id.iv_back)
    lateinit var ivBack: ImageView
    @BindView(R.id.tv_title)
    lateinit var tvName: TextView
    @BindView(R.id.tv_type)
    lateinit var tvType: TextView
    @BindView(R.id.tv_level)
    lateinit var tvLevel: TextView
    @BindView(R.id.tv_qul)
    lateinit var tvQul: TextView
    @BindView(R.id.tv_equip_property)
    lateinit var tvProperty: TextView
    @BindView(R.id.rv_access)
    lateinit var rvAccess: RecyclerView
    @BindView(R.id.fab)
    lateinit var fab: FloatingActionButton

    private var equip = Equip()
    private var dataList = ArrayList<String>()//装备数据
    private var advanceList = ArrayList<String>()//进阶
    private var exclusiveList: List<String> = ArrayList()//专属

    override val layoutId: Int
        get() = R.layout.activity_equip_detail

    @SuppressLint("SetTextI18n")
    override fun initData() {
        val equipId = intent.getStringExtra("id")
        equip = DataSupport.where("id = ?", equipId).find(Equip::class.java)[0]
        ivBack.setOnClickListener { onBackPressed() }
        fab.setOnClickListener { startActivityFinish(MainActivity::class.java) }

        /**
         * 基础属性
         */
        val quality = equip.quality
        val color = resources.getColor(AppUtils.getColor(quality))
        val equipName = equip.equipName
        tvName.text = equipName
        ivEquip.setImageResource(equip.imgId)
        tvLevel.text = "Lv ${equip.level}"
        tvLevel.setTextColor(color)
        tvType.text = equip.type
        tvType.setTextColor(color)
        tvQul.text = quality
        tvQul.setTextColor(color)
        tvProperty.text = equip.equipmentProperty
        tvProperty.visibility = View.VISIBLE

        /**
         * 合成装备
         */
        dataList = equip.dataList as ArrayList<String>
        val accessAdapter = AccessAdapter(dataList)
        accessAdapter.setOnItemClickListener { adapter, view, position ->

        }
        rvAccess.isFocusable = false
        rvAccess.isNestedScrollingEnabled = false
        rvAccess.adapter = accessAdapter

        /**
         * 进阶装备
         */
        advanceList = equip.advanceList as ArrayList<String>
        if (advanceList.size == 0){
            if (equipName == "真·卡拉菲米亚,神圣之剑" || equipName == "真·埃克斯米亚,不洁之刃") {
                advanceList.add("斩神,亚特兰蒂斯的毁灭圣剑")
            }
            if (equipName == "深渊指环" || equipName == "圣光之戒") {
                advanceList.add("德瑞诺斯,深渊领主的戒指")
            }
            if (equipName == "冬日的玫瑰" || equipName == "极冰之牙") {
                advanceList.add("霜枭之戒")
            }
            val equipList = DataSupport.where("access like ?", "%$equipName%").find(Equip::class.java)
            for (i in 0 until equipList.size) {
                val mEquip = equipList[i]
                val mList = mEquip.dataList
                for (j in 0 until mList.size) {
                    if (mList[j] == equipName) {
                        advanceList.add(mEquip.equipName)
                    }
                }
            }
            if (advanceList.isNotEmpty()){
                equip.advanceList = advanceList
                equip.save()
            }
        }
        if (advanceList.size != 0) {
            val viewStub = findViewById<ViewStub>(R.id.vs_advance)
            if (viewStub != null) {
                val inflatedView = viewStub.inflate()
                val advanceView = inflatedView.findViewById<RecyclerView>(R.id.rv_advance)
                val adapter = AccessAdapter(advanceList)
                adapter.setOnItemClickListener{ adapter, view, position ->

                }
                advanceView.adapter = adapter
                advanceView.isFocusable = false
                advanceView.isNestedScrollingEnabled = false
            }
        }

        /**
         * 专属
         */
        if (equip.exclusive != "") {
            exclusiveList = equip.exclusive.split("\n")
            val viewStub = findViewById<ViewStub>(R.id.vs_exclusive)
            if (viewStub != null) {
                val inflatedView = viewStub.inflate()
                val layout = inflatedView.findViewById<LinearLayout>(R.id.ll_exclusive)
                for (i in exclusiveList.indices) {
                    val v = LayoutInflater.from(this).inflate(R.layout.layout_exclusive, layout, false)
                    val tvExclusive = v.findViewById<TextView>(R.id.tv_equip_exclusive)
                    val exclusive = exclusiveList[i]
                    tvExclusive.text = exclusive
                    tvExclusive.setOnClickListener {
                    }
                    layout.addView(v)
                }
            }
        }
    }
}

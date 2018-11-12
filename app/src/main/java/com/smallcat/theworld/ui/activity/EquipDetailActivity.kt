package com.smallcat.theworld.ui.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
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
import com.smallcat.theworld.model.db.Hero
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
    private var dialog: Dialog? = null

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
        val color = AppUtils.getColor(mContext, quality)
        val equipName = equip.equipName
        tvName.text = equipName
        ivEquip.setImageResource(equip.imgId)
        tvLevel.text = "Lv ${equip.level}"
        tvLevel.setTextColor(color)
        tvType.text = equip.type
        tvType.setTextColor(color)
        tvQul.text = quality
        tvQul.setTextColor(color)
        if (equip.equipmentProperty.isNotEmpty()) {
            tvProperty.text = equip.equipmentProperty
            tvProperty.visibility = View.VISIBLE
        }

        /**
         * 合成装备
         */
        dataList = equip.dataList as ArrayList<String>
        val accessAdapter = AccessAdapter(dataList)
        accessAdapter.setOnItemClickListener { _, _, position ->
            var name = dataList[position]
            /**
             * 2选1的情况
             */
            if (name.contains("/")) {
                dialog = Dialog(mContext, R.style.CustomDialog)
                val v1 = LayoutInflater.from(mContext).inflate(R.layout.dialog_choose, null)
                dialog!!.setContentView(v1)
                dialog!!.setCanceledOnTouchOutside(true)
                dialog!!.setCancelable(true)
                val tvName1 = v1.findViewById<TextView>(R.id.tv_name)
                val tvName2 = v1.findViewById<TextView>(R.id.tv_name2)
                val choose1 = name.substring(0, name.indexOf('/'))
                val choose2 = name.substring(name.indexOf('/') + 1)
                tvName1.text = choose1
                tvName2.text = choose2
                tvName1.setOnClickListener {
                    AppUtils.goEquipDetailActivity(mContext, choose1)
                    dialog!!.dismiss()
                }
                tvName2.setOnClickListener {
                    AppUtils.goEquipDetailActivity(mContext, choose2)
                    dialog!!.dismiss()
                }
                dialog!!.show()
                return@setOnItemClickListener
            }
            /**
             * boss掉落的情况
             */
            if (name.contains("[")) {
                name = name.substring(0, name.indexOf('['))
                if (name == "远古法爷" || name == "巨人法爷") {
                    name = "法爷"
                }
                AppUtils.goBossDetailActivity(mContext, name)
                return@setOnItemClickListener
            }

            /**
             * 正常情况
             */
            if (!AppUtils.goEquipDetailActivity(mContext, name)) {
                AppUtils.goBossDetailActivity(mContext, name)
            }
        }
        rvAccess.isFocusable = false
        rvAccess.isNestedScrollingEnabled = false
        rvAccess.adapter = accessAdapter

        /**
         * 进阶装备
         */
        advanceList = equip.advanceList as ArrayList<String>
        if (advanceList.size == 0) {
            val equipList = DataSupport.where("access like ?", "%$equipName%").find(Equip::class.java)
            for (i in 0 until equipList.size) {
                val mEquip = equipList[i]
                val mList = mEquip.dataList
                for (j in 0 until mList.size) {
                    if (mList[j] == equipName) {
                        advanceList.add(mEquip.equipName)
                    } else if (mList[j].contains("/")) {
                        val choose1 = mList[j].substring(0, mList[j].indexOf('/'))
                        val choose2 = mList[j].substring(mList[j].indexOf('/') + 1)
                        if (equipName == choose1 || equipName == choose2) {
                            advanceList.add(mEquip.equipName)
                        }
                    }
                }
            }
            if (advanceList.isNotEmpty()) {
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
                adapter.setOnItemClickListener { _, _, position ->
                    AppUtils.goEquipDetailActivity(mContext, advanceList[position])
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
                        val name = exclusive.substring(0, exclusive.indexOf('-'))
                        val data = DataSupport.select("heroName", "imgId")
                                .where("heroName = ?", name).find(Hero::class.java)
                        data?.let { list ->
                            Intent(this@EquipDetailActivity, CareerDetailActivity::class.java).apply {
                                putExtra("name", list[0].heroName)
                                putExtra("imgId", list[0].imgId)
                                putExtra("position", 2)
                                startActivity(this)
                            }
                        }
                    }
                    layout.addView(v)
                }
            }
        }
    }

    override fun onDestroy() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
        super.onDestroy()
    }
}

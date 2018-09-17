package com.smallcat.theworld.ui.activity

import android.annotation.SuppressLint
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseActivity
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.model.db.Material
import com.smallcat.theworld.ui.adapter.AccessAdapter
import com.smallcat.theworld.utils.AppUtils
import org.litepal.crud.DataSupport
import java.util.ArrayList

class OtherDetailActivity : BaseActivity() {

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
    @BindView(R.id.tv_equip_property)
    lateinit var tvProperty: TextView
    @BindView(R.id.rv_access)
    lateinit var rvAccess: RecyclerView
    @BindView(R.id.fab)
    lateinit var fab: FloatingActionButton

    private lateinit var material : Material
    private lateinit var mMaterialName: String

    private var dataList = ArrayList<String>()//装备数据
    private val advanceList = ArrayList<String>()//进阶

    override val layoutId: Int
        get() = R.layout.activity_equip_detail

    @SuppressLint("SetTextI18n")
    override fun initData() {
        val mMaterialId = intent.getStringExtra("id")
        val equips = DataSupport.where("id = ?", mMaterialId).find(Material::class.java)

        ivBack.setOnClickListener { onBackPressed() }
        fab.setOnClickListener { startActivityFinish(MainActivity::class.java) }

        material = equips[0]
        mMaterialName = material.materialName
        tvName.text = mMaterialName

        val color = resources.getColor(R.color.firebrick)
        tvType.text = material.type
        tvType.setTextColor(color)
        tvLevel.text = "Lv 0"
        tvLevel.setTextColor(color)

        ivEquip.setImageResource(material.imgId)
        if (material.effect.isNotEmpty()){
            tvProperty.visibility = View.VISIBLE
            tvProperty.text = material.effect
        }
        dataList = material.dataList as ArrayList<String>

        /**
         * 获取途径
         */
        val accessAdapter = AccessAdapter(dataList)
        accessAdapter.setOnItemClickListener { adapter, view, position ->

        }
        rvAccess.isFocusable = false
        rvAccess.isNestedScrollingEnabled = false
        rvAccess.adapter = accessAdapter

        /**
         * 合成列表
         */
        if (!mMaterialName.contains("粉末")) {
            val materialList = DataSupport
                    .where("access like ?", "%$mMaterialName%")
                    .find(Material::class.java)
            val equipList = DataSupport
                    .where("access like ?", "%$mMaterialName%")
                    .find(Equip::class.java)
            var result = materialList.size
            for (i in 0 until result) {
                advanceList.add(materialList[i].materialName)
            }
            result = equipList.size
            for (i in 0 until result) {
                advanceList.add(equipList[i].equipName)
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
    }

}

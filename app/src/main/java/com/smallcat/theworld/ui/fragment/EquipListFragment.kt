package com.smallcat.theworld.ui.fragment

import android.content.Intent
import android.os.Bundle
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseFragment
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.ui.activity.EquipDetailActivity
import com.smallcat.theworld.ui.adapter.EquipAdapter
import kotlinx.android.synthetic.main.fragment_equip_list.*
import org.litepal.crud.DataSupport
import java.util.ArrayList


class EquipListFragment : BaseFragment() {

    private var mEquipList: List<Equip> = ArrayList()
    private val tabTitles = arrayOf("武器", "头盔", "衣服", "饰品", "翅膀")
    private var flag: Int = 0

    companion object {
        fun newInstance(position: Int): EquipListFragment {
            val bundle = Bundle()
            bundle.putInt("index", position)
            val fragment = EquipListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_equip_list

    override fun initData() {
        val bundle = this.arguments
        if (bundle != null) {
            flag = bundle.getInt("index")
        }
        val type = tabTitles[flag]
        mEquipList = DataSupport.where("type = ?", type).find(Equip::class.java)
        val adapter = EquipAdapter(mEquipList as MutableList<Equip>?)
        rv_equip.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(mContext, EquipDetailActivity::class.java)
            intent.putExtra("id", mEquipList[position].id.toString())
            startActivity(intent)
        }
    }
}

package com.smallcat.theworld.ui.fragment

import android.content.Intent
import android.os.Bundle
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseFragment
import com.smallcat.theworld.model.db.Material
import com.smallcat.theworld.ui.activity.OtherDetailActivity
import com.smallcat.theworld.ui.adapter.OtherAdapter
import kotlinx.android.synthetic.main.fragment_other.*
import org.litepal.crud.DataSupport


class OtherFragment : BaseFragment() {

    private val tabTitles = arrayOf("材料", "徽章", "其他")

    companion object {
        fun newInstance(position: Int): OtherFragment {
            val bundle = Bundle()
            bundle.putInt("index", position)
            val fragment = OtherFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_other

    override fun initData() {
        val flag = arguments?.getInt("index",0)!!
        val materials = DataSupport.where("type = ?", tabTitles[flag]).find(Material::class.java)
        val adapter = OtherAdapter(materials)
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(mContext, OtherDetailActivity::class.java)
            intent.putExtra("id", materials[position].id.toString())
            startActivity(intent)
        }
        rv_list.adapter = adapter
    }

}

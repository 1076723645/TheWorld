package com.smallcat.theworld.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.Material
import com.smallcat.theworld.ui.activity.OtherDetailActivity
import com.smallcat.theworld.ui.adapter.OtherAdapter
import me.yokeyword.fragmentation.SupportFragment
import org.litepal.crud.DataSupport
import java.util.*


/**
 *
 * @author hui
 * @date 2018/1/4
 */

class OtherFragment : SupportFragment() {

    private var materials: List<Material> = ArrayList()
    private val tabTitles = arrayOf("材料", "徽章", "其他")
    private var flag: Int = 0
    private var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            flag = bundle.getInt("index")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_other, container, false)
        initData()
        return mView
    }

    private fun initData() {
        val type = tabTitles[flag]
        materials = DataSupport.where("type = ?", type).find(Material::class.java)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val recyclerView = mView!!.findViewById<RecyclerView>(R.id.rv_list)
        val adapter = OtherAdapter(materials)
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(context, OtherDetailActivity::class.java)
            intent.putExtra("id", materials[position].id.toString())
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //super.onSaveInstanceState(outState);
    }

    companion object {
        fun newInstance(position: Int): OtherFragment {
            val bundle = Bundle()
            bundle.putInt("index", position)
            val weatherFragment = OtherFragment()
            weatherFragment.arguments = bundle
            return weatherFragment
        }
    }
}

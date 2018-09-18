package com.smallcat.theworld.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.ui.activity.EquipDetailActivity
import com.smallcat.theworld.ui.adapter.EquipAdapter
import me.yokeyword.fragmentation.SupportFragment
import org.litepal.crud.DataSupport


/**
 * @author Administrator
 */
class EquipListFragment : SupportFragment() {

    private lateinit var mEquipList: List<Equip>
    private val tabTitles = arrayOf("武器", "头盔", "衣服", "饰品", "翅膀")
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
        mView = inflater.inflate(R.layout.fragment_equip_list, container, false)
        initData()
        return mView
    }

    private fun initData() {
        val type = tabTitles[flag]
        mEquipList = DataSupport.where("type = ?", type).find(Equip::class.java)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val recyclerView = mView!!.findViewById<RecyclerView>(R.id.rv_equip)
        val adapter = EquipAdapter(mEquipList)
        adapter.setOnItemClickListener(object : EquipAdapter.OnItemClickListener{
            override fun onItemClick(adapter: EquipAdapter, view: View, position: Int) {
                val intent = Intent(context, EquipDetailActivity::class.java)
                intent.putExtra("id", mEquipList[position].id.toString())
                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //super.onSaveInstanceState(outState);
    }

    companion object {

        fun newInstance(position: Int): EquipListFragment {
            val bundle = Bundle()
            bundle.putInt("index", position)
            val fragment = EquipListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}

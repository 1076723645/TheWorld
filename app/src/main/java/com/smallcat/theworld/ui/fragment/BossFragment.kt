package com.smallcat.theworld.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.Boss
import com.smallcat.theworld.ui.activity.BossDetailActivity
import com.smallcat.theworld.ui.adapter.BossAdapter
import com.smallcat.theworld.utils.DataUtil
import me.yokeyword.fragmentation.SupportFragment


/**
 * @author hui
 * @date 2018/1/3
 */
class BossFragment : SupportFragment() {

    private var mView: View? = null
    private lateinit var list:List<Boss>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_boss, container, false)
        return mView
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val recyclerView = mView!!.findViewById<RecyclerView>(R.id.rv_boss)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        list = DataUtil.getBossList()
        val adapter = BossAdapter(list)
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(context, BossDetailActivity::class.java)
            intent.putExtra("id", list[position].id.toString())
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
    }
}

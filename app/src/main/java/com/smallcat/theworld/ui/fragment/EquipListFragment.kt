package com.smallcat.theworld.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxFragment
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.ui.activity.EquipDetailActivity
import com.smallcat.theworld.ui.adapter.EquipAdapter
import com.smallcat.theworld.utils.AppUtils
import com.smallcat.theworld.utils.resiliencyScreen
import com.smallcat.theworld.utils.sharedPref
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.litepal.crud.DataSupport


/**
 * @author Administrator
 */
class EquipListFragment : RxFragment() {

    private lateinit var mEquipList: List<Equip>
    private lateinit var adapter: EquipAdapter
    private val tabTitles = arrayOf("我的", "武器", "头盔", "衣服", "饰品", "翅膀")
    private var flag: Int = 0
    private var isBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            flag = it.getInt("index")
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_equip_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = mView.findViewById<RecyclerView>(R.id.rv_equip)
        adapter = EquipAdapter(null)
        adapter.setOnItemClickListener { _, _, position ->
            Intent(context, EquipDetailActivity::class.java).apply {
                putExtra("id", mEquipList[position].id.toString())
                startActivity(this)
            }
        }
        adapter.openLoadAnimation()
        adapter.emptyView = AppUtils.getEmptyView(mContext, "数据加载中")
        recyclerView.adapter = adapter
        isBack = mContext.sharedPref.isBack
        recyclerView.resiliencyScreen()
        loadData()
    }

    override fun initView() {
    }

    private fun loadData() {
        addSubscribe(Observable.create<List<Equip>> {
            val list = if (isBack) {
                DataSupport.where("type = ?", tabTitles[flag]).order("id desc").find(Equip::class.java)
            } else {
                DataSupport.where("type = ?", tabTitles[flag]).find(Equip::class.java)
            }
            it.onNext(list)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    showList(it)
                })
    }

    private fun showList(data: List<Equip>) {
        mEquipList = data
        adapter.setNewData(mEquipList)
    }

    companion object {
        fun newInstance(position: Int): EquipListFragment =
                EquipListFragment().apply {
                    arguments = Bundle().apply {
                        putInt("index", position)
                    }
                }
    }

}

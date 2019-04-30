package com.smallcat.theworld.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxFragment
import com.smallcat.theworld.model.bean.MsgEvent
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.ui.activity.EquipDetailActivity
import com.smallcat.theworld.ui.adapter.EquipAdapter
import com.smallcat.theworld.utils.AppUtils
import com.smallcat.theworld.utils.RxBus
import com.smallcat.theworld.utils.sharedPref
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.litepal.crud.DataSupport


/**
 * @author Administrator
 */
class MyEquipListFragment : RxFragment() {

    private lateinit var mEquipList: List<Equip>
    private lateinit var adapter: EquipAdapter
    private var isBack = false

    override val layoutId: Int
        get() = R.layout.fragment_my_equip_list

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
        adapter.emptyView = AppUtils.getEmptyView(mContext, "您还没有添加装备哦")
        recyclerView.adapter = adapter
        registerEvent()
    }

    override fun initView() {
        isBack = mContext.sharedPref.isBack
        loadData()
    }

    private fun loadData() {
        addSubscribe(Observable.create<List<Equip>> {
            val list = DataSupport.where("isAdd = ?", "1").find(Equip::class.java)
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

    private fun registerEvent(){
        addSubscribe(RxBus.toObservable(MsgEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    loadData()
                })
    }
}

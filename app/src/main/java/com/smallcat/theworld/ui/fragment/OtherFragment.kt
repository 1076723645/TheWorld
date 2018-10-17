package com.smallcat.theworld.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxFragment
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.ui.activity.EquipDetailActivity
import com.smallcat.theworld.ui.adapter.OtherAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.litepal.crud.DataSupport


/**
 *
 * @author hui
 * @date 2018/1/4
 */

class OtherFragment : RxFragment() {

    private lateinit var mEquipList: List<Equip>
    private lateinit var adapter: OtherAdapter
    private val tabTitles = arrayOf("材料", "徽章", "其他")
    private var flag: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            flag = bundle.getInt("index")
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_other

    override fun initView() {
        val recyclerView = mView.findViewById<RecyclerView>(R.id.rv_list)
        adapter = OtherAdapter(null)
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(context, EquipDetailActivity::class.java)
            intent.putExtra("id", mEquipList[position].id.toString())
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        loadData()
    }

    private fun loadData(){
        addSubscribe(Observable.create<List<Equip>> {
            val list  = DataSupport.select("equipName", "imgId")
                    .where("type = ?", tabTitles[flag]).find(Equip::class.java)
            it.onNext(list)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    showList(it)
                })
    }

    private fun showList(data : List<Equip>) {
        mEquipList = data
        adapter.setNewData(mEquipList)
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

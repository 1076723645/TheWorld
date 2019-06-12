package com.smallcat.theworld.ui.fragment


import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxFragment
import com.smallcat.theworld.model.db.Boss
import com.smallcat.theworld.ui.activity.BossDetailActivity
import com.smallcat.theworld.ui.adapter.BossAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.litepal.crud.DataSupport


/**
 * @author hui
 * @date 2018/1/3
 */
class BossFragment : RxFragment() {

    private lateinit var list: List<Boss>
    private lateinit var adapter: BossAdapter

    override val layoutId: Int
        get() = R.layout.fragment_boss

    override fun initView() {
        val recyclerView = mView.findViewById<RecyclerView>(R.id.rv_boss)
        adapter = BossAdapter(null)
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(mContext, BossDetailActivity::class.java)
            intent.putExtra("id", list[position].id.toString())
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        loadData()
    }

    private fun loadData() {
        addSubscribe(Observable.create<List<Boss>> {
            val list = DataSupport.findAll(Boss::class.java)
            it.onNext(list)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    showList(it)
                })
    }

    private fun showList(data: List<Boss>) {
        list = data
        adapter.setNewData(list)
    }
}

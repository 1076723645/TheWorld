package com.smallcat.theworld.ui.fragment

import android.os.Bundle
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxFragment
import com.smallcat.theworld.model.db.HeroStrategy
import com.smallcat.theworld.ui.activity.WebActivity
import com.smallcat.theworld.ui.adapter.HeroStrategyAdapter
import com.smallcat.theworld.utils.AppUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_strategy.*
import org.litepal.crud.DataSupport


class HeroStrategyFragment : RxFragment() {

    private var heroName: String? = ""
    private lateinit var adapter: HeroStrategyAdapter
    private val list = ArrayList<HeroStrategy>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            heroName = it.getString(TAG)
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_strategy

    override fun initView() {
        adapter = HeroStrategyAdapter(list)
        adapter.emptyView = AppUtils.getEmptyView(mContext, "期待投稿")
        adapter.setOnItemClickListener { _, _, position ->
            startActivity(WebActivity.getIntent(mContext, list[position].address))
        }
        rv_list.adapter = adapter
        loadData()
    }

    private fun loadData(){
        addSubscribe(Observable.create<List<HeroStrategy>> {
            val list  = DataSupport.where("heroName = ?", heroName).find(HeroStrategy::class.java)
            it.onNext(list)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    showData(it)
                })
    }

    private fun showData(data: List<HeroStrategy>){
        list.clear()
        list.addAll(data)
        adapter.setNewData(list)
    }

    companion object {
        const val TAG = "HeroStrategyFragment"
        @JvmStatic
        fun newInstance(name: String) =
                HeroStrategyFragment().apply {
                    arguments = Bundle().apply {
                        putString(TAG, name)
                    }
                }
    }
}

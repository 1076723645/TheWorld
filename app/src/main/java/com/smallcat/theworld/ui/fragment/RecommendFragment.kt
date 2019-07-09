package com.smallcat.theworld.ui.fragment

import android.os.Bundle
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxFragment
import com.smallcat.theworld.model.db.EquipRecommend
import com.smallcat.theworld.ui.activity.RecommendActivity
import com.smallcat.theworld.ui.adapter.HeroEquipAdapter
import com.smallcat.theworld.utils.AppUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recommand.*
import org.litepal.crud.DataSupport


class RecommendFragment : RxFragment() {

    private var heroName = ""
    private lateinit var adapter: HeroEquipAdapter
    private val list = ArrayList<EquipRecommend>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            heroName = it.getString(TAG, "")
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_recommand

    override fun initView() {
        adapter = HeroEquipAdapter(list)
        adapter.emptyView = AppUtils.getEmptyView(mContext, "别问，问就是异端爆破大自残")
        adapter.setOnItemClickListener { _, _, position ->
            startActivity(RecommendActivity.getIntent(mContext, list[position].id))
        }
        rv_list.adapter = adapter
        loadData()
    }

    private fun loadData(){
        addSubscribe(Observable.create<List<EquipRecommend>> {
            val list  = DataSupport.where("heroName = ?", heroName).find(EquipRecommend::class.java)
            it.onNext(list)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    showData(it)
                })
    }

    private fun showData(data: List<EquipRecommend>){
        list.clear()
        list.addAll(data)
        adapter.setNewData(list)
    }

    companion object {
        private const val TAG = "RecommendFragment"

        @JvmStatic
        fun newInstance(name: String) =
                RecommendFragment().apply {
                    arguments = Bundle().apply {
                        putString(TAG, name)
                    }
                }
    }
}

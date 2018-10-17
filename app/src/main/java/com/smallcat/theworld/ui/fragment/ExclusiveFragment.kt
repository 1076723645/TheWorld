package com.smallcat.theworld.ui.fragment


import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxFragment
import com.smallcat.theworld.model.bean.ImgData
import com.smallcat.theworld.model.db.Exclusive
import com.smallcat.theworld.model.db.Hero
import com.smallcat.theworld.ui.activity.CareerDetailActivity
import com.smallcat.theworld.ui.activity.CareerIntroduceActivity
import com.smallcat.theworld.ui.adapter.ExclusiveAdapter
import com.smallcat.theworld.utils.DataUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.yokeyword.fragmentation.SupportFragment
import org.litepal.crud.DataSupport
import java.util.*

/**
 * @author Administrator
 */
class ExclusiveFragment : RxFragment() {

    private val mData = ArrayList<ImgData>()

    private lateinit var adapter: ExclusiveAdapter

    override val layoutId: Int
        get() = R.layout.fragment_exclusive

    override fun initView() {
        val recyclerView = mView.findViewById<RecyclerView>(R.id.rv_profession)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        adapter = ExclusiveAdapter(mData)
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(context , CareerDetailActivity::class.java)
            intent.putExtra("name", mData[position].name)
            intent.putExtra("imgId", mData[position].imgUrl)
            startActivity(intent)

        }
        recyclerView.adapter = adapter
        loadData()
    }

    private fun loadData(){
        addSubscribe(Observable.create<List<Hero>> {
            val list  = DataSupport.findAll(Hero::class.java)
            it.onNext(list)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    showData(it)
                })
    }

    private fun showData(data:List<Hero>){
        mData.clear()
        for (i in data.indices){
            val imgData = ImgData()
            imgData.imgUrl = data[i].imgId
            imgData.name = data[i].heroName!!
            mData.add(imgData)
        }
        adapter.setNewData(mData)
    }
}


package com.smallcat.theworld.ui.fragment

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxFragment
import com.smallcat.theworld.model.bean.PicData
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.model.db.Exclusive
import com.smallcat.theworld.ui.activity.EquipDetailActivity
import com.smallcat.theworld.ui.adapter.ChoosePicAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_hero.*
import org.litepal.crud.DataSupport

/**
 * @author hui
 * @date 2018/10/10
 */
class HeroFragment : RxFragment() {

    private var list = ArrayList<PicData>()
    private lateinit var adapter: ChoosePicAdapter
    private lateinit var dataList: List<Exclusive>

    private var heroName: String? = ""
    private var mChoosePosition = 0

    override val layoutId: Int
        get() = R.layout.fragment_hero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            heroName = it.getString("name")
        }
    }

    override fun initView() {
        val layoutManager = LinearLayoutManager(mContext)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        mChoosePosition = 0
        adapter = ChoosePicAdapter(list)
        adapter.setOnItemClickListener { _, _, position ->
            list[mChoosePosition].isSelected = false
            mChoosePosition = position
            list[mChoosePosition].isSelected = true
            adapter.setNewData(list)
            tv_detail.text = dataList[position].effect
            tv_equip_name.text = dataList[position].equipName
        }
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter
        loadData()
    }

    private fun loadData(){
        addSubscribe(Observable.create<List<Exclusive>> {
            val list  = DataSupport.where("profession = ?", heroName).find(Exclusive::class.java)
            it.onNext(list)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    showData(it)
                })
    }

    private fun showData(data:List<Exclusive>){
        if (data.isEmpty()){
            tv_empty.visibility = View.VISIBLE
        }else {
            list.clear()
            dataList = data
            for (i in data.indices) {
                val picData = PicData(data[i].imgId, false)
                if (i == 0){
                    picData.isSelected = true
                }
                list.add(picData)
            }
            adapter.setNewData(list)
            tv_equip_name.text = dataList[0].equipName
            tv_equip_name.paint.flags = Paint.UNDERLINE_TEXT_FLAG//下划线
            tv_equip_name.setOnClickListener {
                val equips = DataSupport.select("id")
                        .where("equipName = ?", dataList[mChoosePosition].equipName).find(Equip::class.java)
                if (equips.size != 0) {
                    val intent = Intent(mContext, EquipDetailActivity::class.java)
                    intent.putExtra("id", equips[0].id.toString())
                    mContext.startActivity(intent)
                }
            }
            tv_detail.text = dataList[0].effect
        }
    }


    companion object {

        fun newInstance(name: String): HeroFragment {
            val bundle = Bundle()
            bundle.putString("name", name)
            val fragment = HeroFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}

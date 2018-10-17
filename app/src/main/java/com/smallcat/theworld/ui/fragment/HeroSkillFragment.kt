package com.smallcat.theworld.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxFragment
import com.smallcat.theworld.model.bean.PicData
import com.smallcat.theworld.model.db.Skill
import com.smallcat.theworld.ui.adapter.ChoosePicAdapter
import com.smallcat.theworld.utils.ToastUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_hero_skill.*
import org.litepal.crud.DataSupport

class HeroSkillFragment : RxFragment() {

    private var list = ArrayList<PicData>()
    private lateinit var adapter: ChoosePicAdapter
    private lateinit var dataList: List<Skill>

    private var mChoosePosition = 0
    private var heroName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            heroName = it.getString("name")
        }
    }

    override val layoutId: Int
        get() = R.layout.fragment_hero_skill

    @SuppressLint("SetTextI18n")
    override fun initView() {
        val layoutManager = LinearLayoutManager(mContext)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        adapter = ChoosePicAdapter(list)
        adapter.setOnItemClickListener { _, _, position ->
            list[mChoosePosition].isSelected = false
            mChoosePosition = position
            list[mChoosePosition].isSelected = true
            adapter.setNewData(list)
            tv_detail.text = dataList[position].skillEffect
            tv_skill_name.text = "${dataList[position].skillName}(${dataList[position].skillKey})"
        }
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter
        loadData()
    }

    private fun loadData(){
        addSubscribe(Observable.create<List<Skill>> {
            val list  = DataSupport.where("heroName = ?", heroName).find(Skill::class.java)
            it.onNext(list)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    showData(it)
                })
    }

    @SuppressLint("SetTextI18n")
    private fun showData(data:List<Skill>){
        if (data.isEmpty()){
            ToastUtil.shortShow("出BUG了")
        }else{
            dataList = data
            list.clear()
            for (i in data.indices) {
                val picData = PicData(dataList[i].imgId, false)
                if (i == 0){
                    picData.isSelected = true
                }
                list.add(picData)
            }
            adapter.setNewData(list)
            tv_skill_name.text = "${dataList[0].skillName}(${dataList[0].skillKey})"
            tv_detail.text = dataList[0].skillEffect
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String) =
                HeroSkillFragment().apply {
                    arguments = Bundle().apply {
                        putString("name", name)
                    }
                }
    }

}

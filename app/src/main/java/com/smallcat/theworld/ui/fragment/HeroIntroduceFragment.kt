package com.smallcat.theworld.ui.fragment


import android.os.Bundle
import com.smallcat.theworld.R
import com.smallcat.theworld.base.RxFragment
import com.smallcat.theworld.model.db.Hero
import com.smallcat.theworld.utils.ToastUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_hero_introduce.*
import org.litepal.crud.DataSupport

class HeroIntroduceFragment : RxFragment() {

    private lateinit var heroName:String

    override val layoutId: Int
        get() = R.layout.fragment_hero_introduce

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            heroName = bundle.getString("name")
        }
    }

    override fun initView() {
        loadData()
    }

    private fun loadData(){
        addSubscribe(Observable.create<List<Hero>> {
            val list  = DataSupport.where("heroName = ?", heroName).find(Hero::class.java)
            it.onNext(list)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    showData(it)
                })
    }

    private fun showData(data:List<Hero>){
        if (data.isEmpty()){
            ToastUtil.shortShow("发生bug了")
        }else{
            val hero = data[0]
            tv_back.text = hero.back
            tv_type.text = hero.type
            tv_distance.text = hero.distance
            tv_main.text = hero.main
            tv_speed.text = hero.speed
            tv_position.text = hero.position
        }
    }

    companion object {
        fun newInstance(name: String): HeroIntroduceFragment {
            val bundle = Bundle()
            bundle.putString("name", name)
            val fragment = HeroIntroduceFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}

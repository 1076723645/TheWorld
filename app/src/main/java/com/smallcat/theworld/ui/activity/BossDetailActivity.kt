package com.smallcat.theworld.ui.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.viewpager.widget.ViewPager
import butterknife.OnClick
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseActivity
import com.smallcat.theworld.base.RxActivity
import com.smallcat.theworld.model.db.Boss
import com.smallcat.theworld.ui.adapter.BossPageAdapter
import kotlinx.android.synthetic.main.activity_boss_detail.*
import kotlinx.android.synthetic.main.normal_toolbar.*
import org.litepal.crud.DataSupport

class BossDetailActivity : RxActivity() {

    private var imgId: Int = 0

    override val layoutId: Int
        get() = R.layout.activity_boss_detail

    override fun initData() {
        val bossId = intent.getStringExtra("id")
        val bossList = DataSupport.where("id = ?", bossId).find(Boss::class.java)
        val boss = bossList[0]
        imgId = boss.imgId

        tv_title.text = boss.bossName
        tv_call.text = boss.call
        tv_hp.text = boss.hp
        tv_nail.text = boss.nail
        tv_power.text = boss.power
        tv_distance.text = boss.distance
        tv_need_lv.text = boss.level
        tv_resistance.text = boss.resistance
        iv_boss.setImageResource(imgId)

        val data = ArrayList<String>()
        data.add(boss.drop!!)
        data.add(boss.skill!!)
        data.add(boss.strategy!!)
        val adapter = BossPageAdapter(supportFragmentManager, data)
        vp_boss.adapter = adapter
        vp_boss.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                vp_boss.resetHeight(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
        vp_boss.offscreenPageLimit = 2
        vp_boss.resetHeight(0)
        tab_boss.setupWithViewPager(vp_boss)
        //DataUtil.reflex(tab_boss)
    }

    @OnClick(R.id.iv_back, R.id.iv_boss)
    fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> onBackPressed()
            R.id.iv_boss -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val intent = Intent(this, ImageShowActivity::class.java)
                intent.putExtra("img", imgId)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, iv_boss, "watch").toBundle())
            }
        }
    }
}


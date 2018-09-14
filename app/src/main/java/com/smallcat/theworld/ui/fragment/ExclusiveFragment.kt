package com.smallcat.theworld.ui.fragment


import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import butterknife.BindView
import com.smallcat.theworld.R
import com.smallcat.theworld.base.BaseFragment
import com.smallcat.theworld.model.bean.ImgData
import com.smallcat.theworld.ui.activity.CareerIntroduceActivity
import com.smallcat.theworld.ui.adapter.ExclusiveAdapter
import com.smallcat.theworld.utils.DataUtil
import java.util.ArrayList

class ExclusiveFragment : BaseFragment() {

    @BindView(R.id.rv_profession)
    lateinit var recyclerView: RecyclerView

    private val mData = ArrayList<ImgData>()
    private val imgList = intArrayOf(R.drawable.ss1, R.drawable.zy_jj, R.drawable.zy_33, R.drawable.zy_js, R.drawable.zy_ck, R.drawable.zy_jx, R.drawable.zy_qs, R.drawable.zy_bbx, R.drawable.zy_skz, R.drawable.zy_fm, R.drawable.zy_my, R.drawable.zy_dc, R.drawable.zy_kz, R.drawable.zy_hq, R.drawable.zy_mq, R.drawable.zy_c8, R.drawable.zy_bf, R.drawable.zy_hf, R.drawable.zy_yf, R.drawable.zy_ff, R.drawable.zy_jl, R.drawable.zy_cn, R.drawable.zy_ms, R.drawable.zy_lj, R.drawable.zy_xf, R.drawable.zy_ws, R.drawable.zz_df)

    override val layoutId: Int
        get() = R.layout.fragment_exclusive

    override fun initData() {
        for (anImgList in imgList) {
            val imgData = ImgData()
            imgData.imgUrl = anImgList
            mData.add(imgData)
        }
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        val adapter = ExclusiveAdapter(mData)
        adapter.setOnItemClickListener { _, view, position ->
            val intent = Intent(mContext , CareerIntroduceActivity::class.java)
            intent.putExtra("name", DataUtil.getProfession(position))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(mActivity, view.findViewById(R.id.iv_profession), "share").toBundle())
            }else{
                mContext.startActivity(intent)
            }
        }
        recyclerView.adapter = adapter
    }

}

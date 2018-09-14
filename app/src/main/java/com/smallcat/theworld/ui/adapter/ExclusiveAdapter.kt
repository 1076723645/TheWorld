package com.smallcat.theworld.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.smallcat.theworld.R
import com.smallcat.theworld.model.bean.ImgData
import com.smallcat.theworld.utils.DataUtil

/**
 * @author smallCut
 * @date 2018/9/12
 */
class ExclusiveAdapter(data: MutableList<ImgData>?) : BaseQuickAdapter<ImgData, BaseViewHolder>(R.layout.item_profession, data) {

    private val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)

    override fun convert(viewHolder: BaseViewHolder, item: ImgData) {
        viewHolder.setText(R.id.tv_profession_name, DataUtil.getProfession(viewHolder.adapterPosition))

        val imageView = viewHolder.getView<ImageView>(R.id.iv_profession)
        //存在记录的高度时先Layout再异步加载图片
        if (item.height > 0) {
            val layoutParams = imageView.layoutParams
            layoutParams.height = item.height
        }
        //获取屏幕宽度
        val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        val display = windowManager.defaultDisplay
        display.getMetrics(dm)
        val screenWidth = dm.widthPixels

        val simpleTarget = object : SimpleTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                if (viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                    if (item.height <= 0) {
                        val width = resource.width
                        val height = resource.height
                        val realHeight = screenWidth * height / width / 2
                        item.height = realHeight
                        val lp = imageView.layoutParams
                        lp.height = realHeight
                        if (width < screenWidth / 2)
                            lp.width = screenWidth / 2
                    }
                    imageView.setImageBitmap(resource)
                }
            }
        }
        Glide.with(mContext).asBitmap().load(item.imgUrl).apply(options).into(simpleTarget)

    }
}
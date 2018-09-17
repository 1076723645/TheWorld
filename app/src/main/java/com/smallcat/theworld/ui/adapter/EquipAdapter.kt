package com.smallcat.theworld.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.smallcat.theworld.R
import com.smallcat.theworld.model.db.Equip
import com.smallcat.theworld.utils.AppUtils
import kotlin.collections.ArrayList

/**
 * Created by smallCut on 2018/7/19.
 */
class EquipAdapter(private val mEquipList: List<Equip>) : RecyclerView.Adapter<EquipAdapter.ViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener? = null
    private val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)

    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val v = LayoutInflater.from(mContext).inflate(R.layout.item_equip, parent, false)
        val holder = ViewHolder(v)
        holder.equipView.setOnClickListener {
            mOnItemClickListener?.onItemClick(this, v, holder.adapterPosition)
        }
        return holder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val equip = mEquipList[position]
        holder.equipName.text = equip.equipName
        holder.equipLevel.text = equip.level + "级"
        val qul = equip.quality
        holder.equipQul.setTextColor(mContext!!.resources.getColor(AppUtils.getColor(qul)))
        holder.equipQul.text = qul
        Glide.with(mContext!!).load(equip.imgId).apply(options).into(holder.image)
    }

    override fun getItemCount(): Int {
        return mEquipList.size
    }

    inner class ViewHolder (var equipView: View) : RecyclerView.ViewHolder(equipView) {
        var image: ImageView = equipView.findViewById(R.id.iv_equip)
        var equipName: TextView = equipView.findViewById(R.id.tv_name)
        var equipQul: TextView = equipView.findViewById(R.id.tv_qul)
        var equipLevel: TextView = equipView.findViewById(R.id.tv_level)
    }

    interface OnItemClickListener {

        /**
         * Callback method to be invoked when an item in this RecyclerView has
         * been clicked.
         *
         * @param adapter  the adpater
         * @param view     The itemView within the RecyclerView that was clicked (this
         * will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         */
        fun onItemClick(adapter: EquipAdapter, view: View, position: Int)
    }

    /**
     * Register a callback to be invoked when an item in this RecyclerView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mOnItemClickListener = listener
    }
}

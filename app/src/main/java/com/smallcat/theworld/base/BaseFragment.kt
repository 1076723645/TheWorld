package com.smallcat.theworld.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import me.yokeyword.fragmentation.SupportFragment

/**
 * Created by smallCut on 2018/4/27.
 */
abstract class BaseFragment : SupportFragment() {

    protected lateinit var mView: View
    protected lateinit var mActivity: Activity
    protected lateinit var mContext: Context
    private var mUnbind: Unbinder? = null

    protected abstract val layoutId: Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = (context as Activity)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(layoutId, null)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUnbind = ButterKnife.bind(this, view)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        lazyLoading()
    }

    override fun onDestroyView() {
        mUnbind!!.unbind()
        super.onDestroyView()
    }

    /**
     * 懒加载
     */
    protected abstract fun lazyLoading()
}
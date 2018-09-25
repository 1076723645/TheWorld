package com.smallcat.theworld.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import me.yokeyword.fragmentation.SupportFragment

/**
 * Created by smallCut on 2018/4/27.
 */
abstract class RxFragment : SupportFragment() {

    protected lateinit var mView: View
    protected lateinit var mActivity: Activity
    protected lateinit var mContext: Context
    private var mCompositeDisposable: CompositeDisposable? = null

    protected abstract val layoutId: Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = (context as Activity?)!!
        mContext = context!!
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(layoutId, null)
        return mView
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        unSubscribe()
        super.onDestroyView()
    }

    private fun unSubscribe() {
        if (mCompositeDisposable != null)
            mCompositeDisposable!!.clear()
    }

    protected fun addSubscribe(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)
    }

    /**
     * 懒加载
     */
    protected abstract fun initView()
}
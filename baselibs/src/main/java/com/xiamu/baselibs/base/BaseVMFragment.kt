package com.xiamu.baselibs.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.baselibs.mvvm.IViewModel

/**
 * Created by zhengxiaobo in 2019-10-28
 */
abstract class BaseVMFragment<VM: BaseViewModel> : Fragment, IFragment{

    /**
     * 是否可见，用于懒加载
     */
    var mVisible: Boolean = false
    /**
     * 是否第一次加载，用于懒加载
     */
    var mFirst: Boolean = true
    var mRootView: View?= null

    lateinit var mViewModel: VM

    constructor(){
        setArguments(Bundle())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initVM()
        mRootView = initView(inflater, container, savedInstanceState)
        if (mVisible && mFirst){
            onFragmentVisibleChange(true)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(savedInstanceState)
        startObserve()
    }

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(mViewModel)
        }
    }

    open fun providerVMClass(): Class<VM>? = null

    open fun startObserve(){
        mViewModel?.mException?.observe(this, Observer { it?.let { onError(it) } })
    }

    open fun onError(e: Throwable) {}


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mVisible = isVisibleToUser
        if (mRootView == null)
            return
        //可见，并且首次加载时才调用
        onFragmentVisibleChange(mVisible && mFirst)
    }

    /**
     * 当前 Fragment 可见状态发生变化时会回调该方法。
     * 如果当前 Fragment 是第一次加载，等待 onCreateView 后才会回调该方法，
     * 其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作.
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    fun onFragmentVisibleChange(isVisible: Boolean){}


    override fun onDestroy() {
        lifecycle.removeObserver(mViewModel)
        this.mRootView = null
        super.onDestroy()
    }

}
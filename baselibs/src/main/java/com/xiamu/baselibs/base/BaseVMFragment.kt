package com.xiamu.baselibs.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
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
abstract class BaseVMFragment<VM: BaseViewModel> : Fragment{

    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    /**
     * 初始化 View
     */
    abstract fun initView(view: View)

    /**
     * 加载布局
     */
    @LayoutRes
    abstract fun attachLayoutRes(): Int

    lateinit var mViewModel: VM

    constructor(){
        setArguments(Bundle())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initVM()
        return inflater.inflate(attachLayoutRes(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView(view)
        lazyLoadDataIfPrepared()
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
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }


    override fun onDestroy() {
        lifecycle.removeObserver(mViewModel)
        super.onDestroy()
    }

}
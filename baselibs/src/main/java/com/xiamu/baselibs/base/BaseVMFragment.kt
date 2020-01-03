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
import com.kingja.loadsir.callback.Callback
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.baselibs.mvvm.IViewModel
import com.kingja.loadsir.callback.Callback.OnReloadListener
import com.kingja.loadsir.core.LoadSir
import com.kingja.loadsir.core.LoadService
import com.xiamu.baselibs.widget.loadsir.EmptyCallback
import com.xiamu.baselibs.widget.loadsir.LoadingCallback
import com.xiamu.baselibs.widget.loadsir.RetryCallback


/**
 * Created by zhengxiaobo in 2019-10-28
 */
abstract class BaseVMFragment<VM: BaseViewModel> : Fragment, IView{

    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false

    private lateinit var mRootView: View

    private lateinit var mLoadService: LoadService<Any>

    /**
     * 是否使用loadsir，默认不使用
     */
    private fun useLoadSir(): Boolean = true

    /**
     * 加载布局
     */
    @LayoutRes
    abstract fun attachLayoutRes(): Int

    /**
     * 初始化 View
     */
    abstract fun initView(view: View)

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    lateinit var mViewModel: VM

    constructor(){
        setArguments(Bundle())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initVM()
        mRootView = inflater.inflate(attachLayoutRes(), null)

        return if (useLoadSir()){
            mLoadService =
                LoadSir.getDefault().register(mRootView) { v -> onPageRetry(v) }
            mLoadService.loadLayout
        } else {
            mRootView
        }

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

    /**
     * 页面重试
     */
    protected fun onPageRetry(v: View?) {

    }

    override fun showPageLoading() {
        if (mLoadService != null) {
            this.mLoadService.showCallback(LoadingCallback::class.java)
        }
    }

    override fun showPageEmpty() {
        if (mLoadService != null) {
            mLoadService.showCallback(EmptyCallback::class.java)
        }
    }

    override fun showPageError() {
        if (mLoadService != null) {
            mLoadService.showCallback(RetryCallback::class.java)
        }
    }

    override fun showPageContent() {
        if (mLoadService != null) {
            mLoadService.showSuccess()
        }
    }

    override fun onDestroy() {
        lifecycle.removeObserver(mViewModel)
        super.onDestroy()
    }

}
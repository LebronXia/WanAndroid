package com.xiamu.baselibs.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.xiamu.baselibs.widget.loadsir.EmptyCallback
import com.xiamu.baselibs.widget.loadsir.LoadingCallback
import com.xiamu.baselibs.widget.loadsir.RetryCallback

/**
 * Created by zhengxiaobo in 2019-10-30
 */
abstract class BaseActivity : AppCompatActivity(){

   // lateinit var mBinding : DB
   protected var mLoadService: LoadService<Any> ?= null

    /**
     * 是否使用loadsir，默认不使用
     */
    open fun useLoadSir(): Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        //mBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        initVM()
        initBinding()
        if (useLoadSir()){
            this.mLoadService = LoadSir.getDefault().register(this, object : Callback.OnReloadListener{
                override fun onReload(v: View?) {
                    onPageRetry(v)
                }
            })
        }
        initData()
        initView()

    }

    abstract fun getLayoutResId() : Int
    abstract fun initView()
    abstract fun initData()
    open fun initVM(){}
    open fun initBinding(){}

    /**
     * 页面重试
     */
    private fun onPageRetry(v: View?) {

    }

    fun showPageLoading() {
        mLoadService?.showCallback(LoadingCallback::class.java)
    }

    fun showPageEmpty() {
        mLoadService?.showCallback(EmptyCallback::class.java)
    }

    fun showPageError() {
        mLoadService?.showCallback(RetryCallback::class.java)
    }

    fun showPageContent() {
        mLoadService?.showSuccess()
    }


}
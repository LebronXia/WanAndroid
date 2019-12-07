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

/**
 * Created by zhengxiaobo in 2019-10-30
 */
abstract class BaseActivity : AppCompatActivity(){

   // lateinit var mBinding : DB
   protected var mLoadService: LoadService<Any> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        //mBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        initVM()
        initBinding()
        initData()
        initView()
        mLoadService = LoadSir.getDefault().register(this, object : Callback.OnReloadListener{
            override fun onReload(v: View?) {
                onPageRetry(v)
            }
        })

    }

    abstract fun getLayoutResId() : Int
    abstract fun initView()
    abstract fun initData()
    open fun initVM(){}
    open fun initBinding(){}

    /**
     * 页面重试
     */
    protected fun onPageRetry(v: View?) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }


}
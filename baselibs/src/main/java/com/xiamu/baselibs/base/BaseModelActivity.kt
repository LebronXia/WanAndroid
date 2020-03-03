package com.xiamu.baselibs.base

import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xiamu.baselibs.mvvm.BaseViewModel

/**
 * Created by zhengxiaobo in 2019-11-18
 */
abstract class BaseModelActivity<VM: BaseViewModel> : BaseActivity(){

    lateinit var mViewModel: VM

    var context: Context ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        startObserve()
    }

     override fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProvider(this).get(it)
            mViewModel.let(lifecycle::addObserver)
        }
    }

    open fun providerVMClass(): Class<VM>? = null


    open fun startObserve() {
        mViewModel.mException.observe(this, Observer { it?.let { onError(it) } })
    }

    open fun onError(e: Throwable) {}

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.let {
            lifecycle.removeObserver(it)
        }
    }

}
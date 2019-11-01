package com.xiamu.baselibs.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.baselibs.mvvm.IViewModel

/**
 * Created by zhengxiaobo in 2019-10-28
 */
abstract class BaseVmActivity<DB : ViewDataBinding, VM: BaseViewModel> : AppCompatActivity() {

    lateinit var mBinding : DB
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        mBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        initVM()
        startObserve()
        initView()
        initData()
    }

    abstract fun getLayoutResId() : Int
    abstract fun initView()
    abstract fun initData()

    private fun initVM() {
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


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
import com.xiamu.baselibs.util.toast
import java.util.prefs.Preferences

/**
 * Created by zhengxiaobo in 2019-10-28
 */
abstract class BaseVmActivity<DB : ViewDataBinding, VM: BaseViewModel> : BaseActivity() {

    lateinit var mBinding : DB
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserve()
    }

    override fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProvider(this).get(it)
            mViewModel.let(lifecycle::addObserver)
        }
    }

    override fun initBinding() {
        super.initBinding()
        mBinding = DataBindingUtil.setContentView(this, getLayoutResId())
    }

    open fun providerVMClass(): Class<VM>? = null


    open fun startObserve() {
        mViewModel.mException.observe(this, Observer { it?.let { onError(it) } })
        mViewModel.mNetStatus.observe(this, Observer {
            this.toast(it)
        })
    }

    open fun onError(e: Throwable) {}

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.let {
            lifecycle.removeObserver(it)
        }
    }

}


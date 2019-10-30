package com.xiamu.baselibs.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import com.xiamu.baselibs.mvvm.IViewModel

/**
 * Created by zhengxiaobo in 2019-10-28
 */
abstract class BaseVmActivity<DB : ViewDataBinding, VM: IViewModel> : AppCompatActivity() {

    lateinit var mBinding : DB
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        mBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        initView()
        initData()

        if (mViewModel != null)
            lifecycle.addObserver(mViewModel as LifecycleObserver)
    }

    abstract fun getLayoutResId() : Int
    abstract fun initView()
    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.let {
            lifecycle.removeObserver(it as LifecycleObserver)
        }
    }

}


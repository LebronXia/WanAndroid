package com.xiamu.baselibs.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver

/**
 * Created by zhengxiaobo in 2019-10-30
 */
abstract class BaseActivity : AppCompatActivity(){

   // lateinit var mBinding : DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        //mBinding = DataBindingUtil.setContentView(this, getLayoutResId())
        initVM()
        initBinding()
        initData()
        initView()
    }

    abstract fun getLayoutResId() : Int
    abstract fun initView()
    abstract fun initData()
    open fun initVM(){}
    open fun initBinding(){}

    override fun onDestroy() {
        super.onDestroy()
    }


}
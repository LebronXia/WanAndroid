package com.xiamu.wanandroid.mvvm.demo.TencentClassLoading

import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.wanandroid.R

class CustomViewActivity : BaseActivity(){

    override fun useLoadSir(): Boolean = false

    override fun getLayoutResId(): Int {
        return R.layout.activity_tecent_class
    }

    override fun initView() {

    }

    override fun initData() {

    }

}
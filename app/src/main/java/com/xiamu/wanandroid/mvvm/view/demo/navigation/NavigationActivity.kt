package com.xiamu.wanandroid.mvvm.view.demo.navigation

import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.wanandroid.R

/**
 * Created by zxb in 2021/7/25
 */
class NavigationActivity :BaseActivity(){
    override fun useLoadSir(): Boolean = false
    override fun getLayoutResId(): Int {
        return R.layout.activity_navigation
    }

    override fun initView() {
    }

    override fun initData() {
    }

}
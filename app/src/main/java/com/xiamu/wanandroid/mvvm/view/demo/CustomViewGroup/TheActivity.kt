package com.xiamu.wanandroid.mvvm.view.demo.CustomViewGroup

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.wanandroid.R
import kotlinx.android.synthetic.main.activity_customviewgroup.*

/**
 * Created by zxb in 2021/6/21
 */
class TheActivity: BaseActivity() {

    override fun useLoadSir(): Boolean = false

    override fun getLayoutResId(): Int = R.layout.activity_customviewgroup

    override fun initView() {
    }

    override fun initData() {
    }
}
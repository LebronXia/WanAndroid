package com.xiamu.wanandroid.mvvm.demo

import android.view.MotionEvent
import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.wanandroid.R
import kotlinx.android.synthetic.main.activity_two_view.*

class TwoViewActivity : BaseActivity(){

    override fun useLoadSir(): Boolean = false

    override fun getLayoutResId(): Int {
        return R.layout.activity_two_view
    }

    override fun initView() {


    }

    override fun initData() {

    }

}
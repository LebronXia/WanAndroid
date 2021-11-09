package com.xiamu.wanandroid.mvvm.demo

import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.wanandroid.R
import kotlinx.android.synthetic.main.activity_button_text.*
import me.jessyan.autosize.utils.LogUtils

/**
 * Created by zxb in 2021/11/9
 */
class ButtonTextActivity: BaseActivity() {

    override fun useLoadSir(): Boolean = false

    override fun getLayoutResId(): Int = R.layout.activity_button_text

    override fun initView() {
    }

    override fun initData() {

        haha1.setOnClickListener {
            LogUtils.d("haha1")
        }

        haha2.setOnClickListener {
            LogUtils.d("haha2")
        }

        haha3.setOnClickListener {
            LogUtils.d("haha3")
        }

        haha4.setOnClickListener {
            LogUtils.d("haha4")
        }


    }

}
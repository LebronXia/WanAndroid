package com.xiamu.wanandroid.mvvm.view.demo

import android.content.Intent
import android.view.View
import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.view.demo.layoutmanager.LayoutManagerActivity
import kotlinx.android.synthetic.main.activity_my_demo.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by zxb in 2021/1/24
 */
class DemoActivity : BaseActivity(), View.OnClickListener{

    override fun getLayoutResId(): Int  = R.layout.activity_my_demo
    override fun useLoadSir(): Boolean = false

    override fun initView() {
        toolbar.apply {
            title = "Demo"
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }

    }

    override fun initData() {

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.tv_layoutmanager -> {
                startActivity(Intent(this, LayoutManagerActivity::class.java))
            }
            R.id.tv_viewGroup -> {

            }

        }
    }
}
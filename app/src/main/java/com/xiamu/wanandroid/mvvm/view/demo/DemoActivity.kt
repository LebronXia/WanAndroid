package com.xiamu.wanandroid.mvvm.view.demo

import android.content.Intent
import android.view.View
import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.baselibs.util.startKtxActivity
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.demo.ButtonTextActivity
import com.xiamu.wanandroid.mvvm.demo.flow.FlowDemoActivity
import com.xiamu.wanandroid.mvvm.demo.navigation.NavigationActivity
import com.xiamu.wanandroid.mvvm.demo.paging3.PagingDemoActivity
import com.xiamu.wanandroid.mvvm.view.demo.CustomViewGroup.TheActivity
import com.xiamu.wanandroid.mvvm.view.demo.layoutmanager.LayoutManagerActivity
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
                startActivity(Intent(this, TheActivity::class.java))
            }
            R.id.tv_navigation -> {
                startActivity(Intent(this, NavigationActivity::class.java))
            }
            R.id.tv_flow -> {
               // startKtxActivity<FlowDemoActivity>()
                startKtxActivity<ButtonTextActivity>()
            }
            R.id.tv_paging3 -> {
                startKtxActivity<PagingDemoActivity>()
            }
        }
    }

//    inline fun <reified T: Activity> Activity.startActivity(){
//        val intent = Intent(this, javaClass<T>())
//        startActivity(intent)
//    }
}
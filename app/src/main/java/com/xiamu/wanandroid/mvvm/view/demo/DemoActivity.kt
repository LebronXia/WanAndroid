package com.xiamu.wanandroid.mvvm.view.demo

import android.content.Intent
import android.view.View
import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.baselibs.util.startKtxActivity
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvi.MVIHomeActivity
import com.xiamu.wanandroid.mvvm.view.demo.TencentClassLoading.CustomViewActivity
import com.xiamu.wanandroid.mvvm.view.demo.navigation.NavigationActivity
import com.xiamu.wanandroid.mvvm.view.demo.paging3.PagingDemoActivity
import com.xiamu.wanandroid.mvvm.view.demo.recycleviewCountDown.CountRecycActivity
import com.xiamu.wanandroid.mvvm.view.demo.viewpager2.ViewPager2DemoActivity
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
                startKtxActivity<TwoViewActivity>()
                //startKtxActivity<ButtonTextActivity>()
            }
            R.id.tv_paging3 -> {
                startKtxActivity<PagingDemoActivity>()
            }

            R.id.tv_count_recycle -> {
                startKtxActivity<CountRecycActivity>()
            }

            R.id.tv_viewpager2 -> {
                startKtxActivity<ViewPager2DemoActivity>()
            }

            R.id.tecent_class_view -> {
                startKtxActivity<CustomViewActivity>()
            }

            R.id.mvi_home -> {
                startKtxActivity<MVIHomeActivity>()
            }

        }
    }

//    inline fun <reified T: Activity> Activity.startActivity(){
//        val intent = Intent(this, javaClass<T>())
//        startActivity(intent)
//    }
}
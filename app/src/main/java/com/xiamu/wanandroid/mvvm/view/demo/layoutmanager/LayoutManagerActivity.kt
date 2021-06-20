package com.xiamu.wanandroid.mvvm.view.demo.layoutmanager

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.view.adapter.ProjectAdapter
import com.xiamu.wanandroid.mvvm.view.widget.view.layoutmanager.RepeatLayoutManager
import kotlinx.android.synthetic.main.activity_layoutamanger.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by zxb in 2021/1/24
 */
class LayoutManagerActivity: BaseActivity() {

    private val mManagerData: MutableList<String> = ArrayList()
    private var managerAdapter: LayoutAdapter ?= null
    override fun useLoadSir(): Boolean = false

    override fun getLayoutResId(): Int = R.layout.activity_layoutamanger

    override fun initView() {
        toolbar.apply {
            title = "layoutManager"
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }
        managerAdapter = LayoutAdapter(mManagerData)
        recycleview.run {
            adapter = managerAdapter
            layoutManager = RepeatLayoutManager(RecyclerView.HORIZONTAL)
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun initData() {
        for(i: Int in 0 until 20){
            mManagerData.add("" + i)
        }
    }
}



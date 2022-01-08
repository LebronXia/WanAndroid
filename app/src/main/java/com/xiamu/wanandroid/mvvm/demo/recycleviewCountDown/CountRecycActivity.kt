package com.xiamu.wanandroid.mvvm.demo.recycleviewCountDown

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.wanandroid.R
import kotlinx.android.synthetic.main.activity_recycleview_demo.*

/**
 * 带倒计时的RecycleView
 * Created by zxb in 2022/1/8
 */
class CountRecycActivity : BaseActivity(){

    override fun useLoadSir(): Boolean = false

    override fun getLayoutResId(): Int  = R.layout.activity_recycleview_demo

    override fun initView() {

        // 添加测试数据
        val beans = ArrayList<BaseItemBean>()
        for (i in 0..100) {
            // 计算终止时间，这里都是当前时间 + i乘以10s
            val terminalTime = System.currentTimeMillis() + i * 10_000
            // 这里手动计算了ViewType (i%2)+1
            beans.add(BaseItemBean(i.toLong(), terminalTime, (i % 2) + 1))
        }

        val mAdapter = Adapter(beans)
        mAdapter.onItemDeleteClick = { position ->
            // 点击就删除
            beans.removeAt(position)
            mAdapter.notifyItemRemoved(position)
        }

        countRecycle.run {
            layoutManager = LinearLayoutManager(this@CountRecycActivity)
            adapter = mAdapter
        }


    }

    override fun initData() {

    }
}
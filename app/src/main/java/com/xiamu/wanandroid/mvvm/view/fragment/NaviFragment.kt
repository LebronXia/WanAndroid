package com.xiamu.wanandroid.mvvm.view.fragment

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.xiamu.baselibs.base.BaseVMFragment
import com.xiamu.wanandroid.mvvm.model.entry.Article
import com.xiamu.wanandroid.mvvm.model.entry.NaviBean
import com.xiamu.wanandroid.mvvm.view.adapter.NaviListAdapter
import com.xiamu.wanandroid.mvvm.view.adapter.NaviTabAdapter
import com.xiamu.wanandroid.mvvm.viewmodel.NaviViewModel
import com.xiamu.wanandroid.mvvm.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_navigation.*
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView
import android.R
import android.util.Log
import com.xiamu.wanandroid.util.onNetError
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.TabView


/**
 * Created by zhengxiaobo in 2019-11-11
 */
class NaviFragment : BaseVMFragment<NaviViewModel>(){

    private var tablist: MutableList<String> = ArrayList()

    private var flowlist: MutableList<NaviBean> = ArrayList()

    private val mNaviListAdapter by lazy { NaviListAdapter(null) }

    private var index: Int = 0
    private var move : Boolean = false
    private lateinit var manager: LinearLayoutManager

    override fun providerVMClass(): Class<NaviViewModel>? = NaviViewModel::class.java

    override fun attachLayoutRes(): Int {
        return com.xiamu.wanandroid.R.layout.fragment_navigation
    }

    override fun initView(view: View) {
        recycleview.run {
            adapter = mNaviListAdapter
            manager = layoutManager as LinearLayoutManager
            setOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (move) {
                        move = false
                        //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                        val n = index - manager.findFirstVisibleItemPosition()
                        if (0 <= n && n < recyclerView.childCount) {
                            //获取要置顶的项顶部离RecyclerView顶部的距离
                            val top = recyclerView.getChildAt(n).top
                            //最后的移动
                            recyclerView.smoothScrollBy(0, top)
                        }
                    }
                }
            })
        }
        tab.addOnTabSelectedListener(object : VerticalTabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabView?, position: Int) {
            }

            override fun onTabSelected(tab: TabView?, position: Int) {
                scollToPosition(manager, position)
            }

        })
    }

    override fun lazyLoad() {
        //showPageLoading()
        mViewModel.getNaviData()
    }

    override fun startObserve() {
        super.startObserve()

        mViewModel.naviState.observe(this, Observer {

            it?.let {
                showPageContent()
                tablist.clear()
                flowlist.clear()
                it.forEach{  navibean ->
                    tablist.add(navibean.name)
                }
                flowlist.addAll(it)

                tab.run {
                    setTabAdapter(NaviTabAdapter(context, tablist))
                }
                mNaviListAdapter.run {
                    replaceData(flowlist)
                }

            }
        })

    }

    private fun scollToPosition(manager: LinearLayoutManager, n: Int) {
        //滑动到指定的item
        this.index = n//记录一下 在第三种情况下会用到
        //拿到当前屏幕可见的第一个position跟最后一个postion
        var firstItem = manager.findFirstVisibleItemPosition()
        var lastItem = manager.findLastVisibleItemPosition()
        //区分情况
        when {
            n <= firstItem -> //当要置顶的项在当前显示的第一个项的前面时
                recycleview.smoothScrollToPosition(n)
            n <= lastItem -> {
                //当要置顶的项已经在屏幕上显示时
                var top = recycleview.getChildAt(n - firstItem).getTop()
                recycleview.smoothScrollBy(0, top)
            }
            else -> {
                //当要置顶的项在当前显示的最后一项的后面时
                recycleview.smoothScrollToPosition(n)
                //这里这个变量是用在RecyclerView滚动监听里面的
                move = true
            }
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        showPageError()
        activity?.onNetError(e){
            Log.d("activity", e.message)
        }
    }

}
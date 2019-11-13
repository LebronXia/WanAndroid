package com.xiamu.wanandroid.mvvm.view.fragment

import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.xiamu.baselibs.base.BaseVMFragment
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.model.entry.Article
import com.xiamu.wanandroid.mvvm.view.adapter.NaviTabAdapter
import com.xiamu.wanandroid.mvvm.viewmodel.NaviViewModel
import com.xiamu.wanandroid.mvvm.viewmodel.ProjectViewModel
import kotlinx.android.synthetic.main.fragment_navigation.*
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView

/**
 * Created by zhengxiaobo in 2019-11-11
 */
class NaviFragment : BaseVMFragment<NaviViewModel>(){

    private var tablist: MutableList<String> = ArrayList()

    private var flowlist: MutableList<Article> = ArrayList()

    override fun providerVMClass(): Class<NaviViewModel>? = NaviViewModel::class.java

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_navigation
    }

    override fun initView(view: View) {


    }

    override fun lazyLoad() {
        mViewModel.getNaviData()
    }

    override fun startObserve() {
        super.startObserve()

        mViewModel.naviState.observe(this, Observer {

            it?.let {
                it.forEach{  navibean ->
                    tablist.add(navibean.name)
                }

                tab.run {
                    setTabAdapter(NaviTabAdapter(context, tablist))


                }


            }
        })
    }

}
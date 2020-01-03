package com.xiamu.wanandroid.mvvm.view.fragment

import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.xiamu.baselibs.base.BaseVMFragment
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean
import com.xiamu.wanandroid.mvvm.view.adapter.ProjectAdapter
import com.xiamu.wanandroid.mvvm.view.adapter.ProjectPagerAdapter
import com.xiamu.wanandroid.mvvm.view.adapter.WxArtioclePagerAdapter
import com.xiamu.wanandroid.mvvm.viewmodel.ProjectViewModel
import com.xiamu.wanandroid.mvvm.viewmodel.WxArticleViewModel
import kotlinx.android.synthetic.main.activity_knowtree_detail.*

/**
 * Created by zhengxiaobo in 2019-11-09
 */
class ProjectFragment : BaseVMFragment<ProjectViewModel>(){

    private var projectTrees: MutableList<TreeBean> = ArrayList()

    private val pagerAdapter: ProjectPagerAdapter by lazy {
        ProjectPagerAdapter(projectTrees, childFragmentManager)
    }

    override fun providerVMClass(): Class<ProjectViewModel>? = ProjectViewModel::class.java

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_tab_refresh
    }

    override fun initView(view: View) {
        viewPager.run {
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        }

        tabLayout.run {
            setupWithViewPager(viewPager)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
            addOnTabSelectedListener(onTabSelectedListener)
        }
    }

    override fun lazyLoad() {
        mViewModel.getProjecctTree()
    }

    override fun startObserve() {
        super.startObserve()

        mViewModel.projectState.observe(this, Observer {

            showPageContent()
            it?.let {
                projectTrees.clear()
                projectTrees.addAll(it)

                viewPager.run {
                    adapter = pagerAdapter
                    offscreenPageLimit = projectTrees.size
                }

            }
        })
    }

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener{

        override fun onTabReselected(p0: TabLayout.Tab?) {
        }

        override fun onTabUnselected(p0: TabLayout.Tab?) {
        }

        override fun onTabSelected(p0: TabLayout.Tab?) {
            tabLayout?.let {
                viewPager.setCurrentItem(it.selectedTabPosition, false)
            }
        }
    }


}
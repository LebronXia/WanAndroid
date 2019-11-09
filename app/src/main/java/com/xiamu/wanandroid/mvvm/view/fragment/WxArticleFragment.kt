package com.xiamu.wanandroid.mvvm.view.fragment

import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.xiamu.baselibs.base.BaseVMFragment
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean
import com.xiamu.wanandroid.mvvm.view.adapter.KnowledgePagerAdapter
import com.xiamu.wanandroid.mvvm.view.adapter.WxArtioclePagerAdapter
import com.xiamu.wanandroid.mvvm.viewmodel.TreeViewModel
import com.xiamu.wanandroid.mvvm.viewmodel.WxArticleViewModel
import kotlinx.android.synthetic.main.activity_knowtree_detail.*
import timber.log.Timber

/**
 * Created by zhengxiaobo in 2019-11-09
 */
class WxArticleFragment : BaseVMFragment<WxArticleViewModel>(){

    private var trees: MutableList<TreeBean> = ArrayList()

    private val pagerAdapter: WxArtioclePagerAdapter by lazy {
        WxArtioclePagerAdapter(trees, childFragmentManager)
    }

    override fun providerVMClass(): Class<WxArticleViewModel>? = WxArticleViewModel::class.java

    override fun attachLayoutRes(): Int = R.layout.fragment_tab_refresh

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
        mViewModel.getWxArticleData()
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.wxArticleState.observe(this, Observer {

            it?.let {
                trees.clear()
                trees.addAll(it)

                viewPager.run {
                    adapter = pagerAdapter
                    offscreenPageLimit = trees.size
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
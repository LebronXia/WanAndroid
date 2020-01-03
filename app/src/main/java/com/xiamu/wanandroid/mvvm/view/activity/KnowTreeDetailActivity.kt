package com.xiamu.wanandroid.mvvm.view.activity

import com.google.android.material.tabs.TabLayout
import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.baselibs.base.BaseVmActivity
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.constant.AppConstant
import com.xiamu.wanandroid.databinding.KnowTreeBinding
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean
import com.xiamu.wanandroid.mvvm.view.adapter.KnowledgePagerAdapter
import com.xiamu.wanandroid.mvvm.viewmodel.LoginViewModel
import com.xiamu.wanandroid.mvvm.viewmodel.TreeViewModel
import kotlinx.android.synthetic.main.activity_knowtree_detail.*

/**
 * Created by zhengxiaobo in 2019-11-07
 */
class KnowTreeDetailActivity: BaseVmActivity<KnowTreeBinding, TreeViewModel>(){

    private lateinit var toolbarTitle: String
    private var trees: MutableList<TreeBean> = ArrayList()

    private val pagerAdapter: KnowledgePagerAdapter by lazy {
        KnowledgePagerAdapter(trees, supportFragmentManager)
    }

    override fun providerVMClass(): Class<TreeViewModel>? = TreeViewModel::class.java

    override fun getLayoutResId(): Int {
        return R.layout.activity_knowtree_detail
    }

    override fun useLoadSir(): Boolean  = false

    override fun initView() {
        mBinding.viewmodel = mViewModel

        toolbar.run {
            title = toolbarTitle
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }

        viewPager.run {
            adapter = pagerAdapter
            offscreenPageLimit = trees.size
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout) )
        }

        tabLayout.run {
            setupWithViewPager(viewPager)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
            addOnTabSelectedListener(onTabSelectedListener)
        }

    }

    override fun initData() {
        intent.run {
            toolbarTitle = getStringExtra(AppConstant.EXTRA_TREE_NAME)
            getSerializableExtra(AppConstant.EXTRA_TREE_DATA)?.let {
                val data = it as TreeBean
                data.children?.let {
                    trees.addAll(it)
                }
            }
        }
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
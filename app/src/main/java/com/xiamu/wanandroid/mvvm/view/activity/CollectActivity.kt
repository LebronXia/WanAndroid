package com.xiamu.wanandroid.mvvm.view.activity

import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.cxz.wanandroid.widget.RecyclerViewItemDecoration
import com.xiamu.baselibs.base.BaseModelActivity
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.view.adapter.HomeArticleAdapter
import com.xiamu.wanandroid.mvvm.viewmodel.CollectViewModel
import com.xiamu.wanandroid.mvvm.viewmodel.SearchViewModel
import com.xiamu.wanandroid.util.onNetError
import kotlinx.android.synthetic.main.activity_knowtree_detail.*
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 * Created by zhengxiaobo in 2019-11-19
 */
class CollectActivity: BaseModelActivity<CollectViewModel>(){

    private var currentPage = 0

    private val homeArticleAdapter: HomeArticleAdapter by lazy{ HomeArticleAdapter(R.layout.item_homelist, null) }

    private val recyclerViewItemDecoration by lazy {
        RecyclerViewItemDecoration(this@CollectActivity, LinearLayoutManager.VERTICAL)
    }

    override fun providerVMClass(): Class<CollectViewModel>? = CollectViewModel::class.java

    override fun getLayoutResId(): Int {
        return R.layout.activity_refresh_layout
    }

    override fun initView() {
        toolbar.run{
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }

        refreshlayout.run {
            setOnRefreshListener{
                refresh()
            }
        }
        recycleview.run {
            adapter = homeArticleAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        homeArticleAdapter.run {
            setOnLoadMoreListener {
                mViewModel.getCollectList(currentPage)
            }
        }
    }

    override fun initData() {
        refresh()
    }

    private fun refresh(){
        currentPage = 0
        refreshlayout.isRefreshing = true
        homeArticleAdapter.setEnableLoadMore(false)
        mViewModel.getCollectList(currentPage)
    }

    override fun startObserve() {
        super.startObserve()

        mViewModel.collectState.observe(this, Observer {

            it?.let {

                val searchResultBean = it
                homeArticleAdapter.run {

                    if (searchResultBean.offset >= searchResultBean.total){
                        loadMoreEnd()
                        return@let
                    }

                    if (refreshlayout.isRefreshing) replaceData(searchResultBean.datas) else addData(searchResultBean.datas)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }

                refreshlayout.isRefreshing = false
                currentPage ++
            }

        })
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        onNetError(e){
            refreshlayout.isRefreshing = false
            Log.d("CollectActivity", e.message)
        }
    }


}
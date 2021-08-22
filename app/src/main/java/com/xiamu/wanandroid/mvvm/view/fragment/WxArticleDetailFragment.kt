package com.xiamu.wanandroid.mvvm.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.cxz.wanandroid.widget.RecyclerViewItemDecoration
import com.xiamu.baselibs.base.BaseVMFragment
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.constant.AppConstant
import com.xiamu.wanandroid.mvvm.view.adapter.KnowledgeAdapter
import com.xiamu.wanandroid.mvvm.viewmodel.TreeViewModel
import com.xiamu.wanandroid.mvvm.viewmodel.WxArticleViewModel
import com.xiamu.wanandroid.util.onNetError
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 * Created by zhengxiaobo in 2019-11-09
 */
class WxArticleDetailFragment : BaseVMFragment<WxArticleViewModel>(){

    private var cid: Int = 0
    private var currentPage = 0

    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            RecyclerViewItemDecoration(it, LinearLayoutManager.VERTICAL)
        }
    }

    private val treeDetailAdapter : KnowledgeAdapter by lazy { KnowledgeAdapter(null) }

    companion object{
        fun getInstance(cid: Int): KnowledgeFragment{
            var fragment = KnowledgeFragment()
            val args = Bundle()
            args.putInt(AppConstant.EXTRA_TREE_CID, cid)
            fragment.arguments = args
            return fragment
        }
    }

    override fun providerVMClass(): Class<WxArticleViewModel>? = WxArticleViewModel::class.java

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_refresh_layout
    }

    override fun initView(view: View) {

        cid = arguments?.getInt(AppConstant.EXTRA_TREE_CID) ?: 0
        refreshlayout.run {
            setOnRefreshListener{
                refresh()
            }
        }
        recycleview.run {
            adapter = treeDetailAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        treeDetailAdapter.run {
            setOnLoadMoreListener {
                mViewModel.getTreeDetailData(currentPage, cid)
            }
        }
    }

    override fun lazyLoad() {
        refresh()
    }

    private fun refresh(){
        currentPage = 0
        refreshlayout.isRefreshing = true
        treeDetailAdapter.setEnableLoadMore(false)
        mViewModel.getTreeDetailData(currentPage, cid)
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.wxArticleDetailState.observe(this, Observer {

            it?.let {

                val treeDetailBean = it
                treeDetailAdapter.run {

                    if (treeDetailBean.offset >= treeDetailBean.total){
                        loadMoreEnd()
                        return@let
                    }

                    if (refreshlayout.isRefreshing) replaceData(treeDetailBean.datas) else addData(treeDetailBean.datas)
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
        activity?.onNetError(e){
            refreshlayout.isRefreshing = false
            Log.d("activity", e.message?:"")
        }
    }
}
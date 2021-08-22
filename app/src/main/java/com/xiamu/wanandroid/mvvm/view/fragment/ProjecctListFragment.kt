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
import com.xiamu.wanandroid.mvvm.view.adapter.ProjectAdapter
import com.xiamu.wanandroid.mvvm.viewmodel.ProjectViewModel
import com.xiamu.wanandroid.mvvm.viewmodel.WxArticleViewModel
import com.xiamu.wanandroid.util.onNetError
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 * Created by zhengxiaobo in 2019-11-09
 */
class ProjecctListFragment : BaseVMFragment<ProjectViewModel>(){

    private var cid: Int = 0
    private var currentPage = 0

    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            RecyclerViewItemDecoration(it, LinearLayoutManager.VERTICAL)
        }
    }

    private val projectAdapter: ProjectAdapter by lazy { ProjectAdapter(null) }

    companion object{
        fun getInstance(cid: Int): ProjecctListFragment{
            var fragment = ProjecctListFragment()
            val args = Bundle()
            args.putInt(AppConstant.EXTRA_TREE_CID, cid)
            fragment.arguments = args
            return fragment
        }
    }

    override fun providerVMClass(): Class<ProjectViewModel>? = ProjectViewModel::class.java

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
            adapter = projectAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        projectAdapter.run {
            setOnLoadMoreListener {
                mViewModel.getProjectDetailData(currentPage, cid)
            }
        }
    }

    override fun lazyLoad() {
        refresh()
    }

    private fun refresh(){
        currentPage = 0
        refreshlayout.isRefreshing = true
        projectAdapter.setEnableLoadMore(false)
        mViewModel.getProjectDetailData(currentPage, cid)
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.projectListState.observe(this, Observer {
            showPageContent()
            it?.let {

                val treeDetailBean = it
                projectAdapter.run {

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
            Log.d("ProjecctListFragment", e.message?:"")
        }
    }


}
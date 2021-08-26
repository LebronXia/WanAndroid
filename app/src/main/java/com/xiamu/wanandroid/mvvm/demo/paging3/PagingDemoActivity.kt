package com.xiamu.wanandroid.mvvm.demo.paging3

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.chad.library.adapter.base.BaseViewHolder
import com.xiamu.baselibs.base.BaseModelActivity
import com.xiamu.baselibs.util.toast
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.demo.paging3.adapter.PagingArticleAdapter
import com.xiamu.wanandroid.mvvm.demo.paging3.adapter.PagingNormalAdapter
import com.xiamu.wanandroid.mvvm.demo.paging3.adapter.PagingWrapAdapter
import com.xiamu.wanandroid.mvvm.model.entry.Article
import kotlinx.android.synthetic.main.activity_paging3_main.*
import kotlinx.android.synthetic.main.activity_paging3_main.recycleview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by zxb in 2021/8/16
 */
class PagingDemoActivity: BaseModelActivity<Paging3ViewModel>() {

    //private val pagingArticleAdapter = PagingArticleAdapter()

    private val mAdapter = PagingNormalAdapter()

    private val newPagingArticleAdapter = PagingWrapAdapter<Article, BaseViewHolder>(mAdapter){
        mAdapter.setNewData(it)
    }

    override fun useLoadSir(): Boolean = false

    override fun providerVMClass(): Class<Paging3ViewModel> = Paging3ViewModel::class.java

    override fun getLayoutResId(): Int = R.layout.activity_paging3_main

    override fun initView() {
        recycleview?.run {
            adapter = newPagingArticleAdapter.withLoadStateFooter(FooterAdapter{newPagingArticleAdapter.retry()})
        }

        newPagingArticleAdapter.addLoadStateListener {
            when(it.refresh){
                is LoadState.NotLoading ->{
                    showPageContent()
                    progress_bar.visibility = View.INVISIBLE
                    recycleview.visibility = View.VISIBLE

                }
                is LoadState.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                    recycleview.visibility = View.INVISIBLE
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    progress_bar.visibility = View.INVISIBLE
                    toast("Load Error: ${state.error.message}")
                }

            }
        }
    }

    private fun refresh() {
        lifecycleScope.launchWhenCreated {
            mViewModel.getPagingData().collectLatest {
                newPagingArticleAdapter.submitList(pagingData = it)  //触发Paging 3分页功能的核心  协程挂起（suspend）操作
            }

//            lifecycle.repeatOnLifecycle(STARTED){
//                mViewModel.getPagingData().collect {
//                    pagingArticleAdapter.submitData(pagingData = it)  //触发Paging 3分页功能的核心  协程挂起（suspend）操作
//                }
//            }
        }
    }

    override fun initData() {
       refresh()
    }
}
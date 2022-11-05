package com.xiamu.wanandroid.mvi

import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.baselibs.util.toast
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.constant.AppConstant
import com.xiamu.wanandroid.mvi.state.FetchStatus
import com.xiamu.wanandroid.mvi.state.HomeViewAction
import com.xiamu.wanandroid.mvi.state.HomeViewEvent
import com.xiamu.wanandroid.mvi.state.HomeViewState
import com.xiamu.wanandroid.mvi.viewmodel.MVIHomeViewModel
import com.xiamu.wanandroid.mvvm.view.activity.WebActivity
import com.xiamu.wanandroid.mvvm.view.adapter.HomeArticleAdapter
import kotlinx.android.synthetic.main.activity_mvi_home.*

class MVIHomeActivity : BaseActivity(){

    private val viewModel by viewModels<MVIHomeViewModel>()
    private val homeArticleAdapter: HomeArticleAdapter by lazy{ HomeArticleAdapter(R.layout.item_homelist, null) }
    private var isFresh = true

    override fun useLoadSir(): Boolean = false

    override fun getLayoutResId(): Int {
        return  R.layout.activity_mvi_home
    }

    override fun initView() {
        initRecycleview()
        isFresh = true
        homeArticleAdapter.setEnableLoadMore(false)
        viewModel.dispatch(HomeViewAction.OnSwipeRefresh)
    }

    override fun initData() {
        initViewStates()
        initViewEvents()
    }

    private fun initRecycleview() {
        recycleview.run {
            adapter = homeArticleAdapter
            itemAnimator = DefaultItemAnimator()
            //recyclerViewItemDecoration?.let { addItemDecoration(it)}
        }

        homeArticleAdapter.run {
            setOnLoadMoreListener({
                viewModel.dispatch(HomeViewAction.FetchNews)
            }, recycleview)

        }
    }

    private fun initViewStates() {
        viewModel.viewState.run {
            observeState(this@MVIHomeActivity, HomeViewState::article){
                homeArticleAdapter.run {
                    it?.let {
                        if (isFresh) replaceData(it!!.datas) else addData(it!!.datas)
                        isFresh = false
                        setEnableLoadMore(true)
                        loadMoreComplete()
                    }
                }

                if(homeArticleAdapter.data.isEmpty()){
                    showPageEmpty()
                } else {
                    showPageContent()
                }
            }
            observeState(this@MVIHomeActivity, HomeViewState::fetchStatus){
                when(it){
                    is FetchStatus.Fetching -> {

                    }
                    is FetchStatus.NotFetched ->{
                        showPageError()
                        homeArticleAdapter.run {
                            if (isFresh){
                                setEnableLoadMore(true)
                            } else {
                                homeArticleAdapter.loadMoreComplete()
                            }
                        }
                    }
                    is FetchStatus.Fetching -> {

                    }
                }

            }

        }
    }

    private fun initViewEvents() {

        viewModel.viewEvents.observeEvent(this){
            when(it){

                is HomeViewEvent.ShowToast -> {
                    it.message?.let {
                        toast(it)
                    }
                }
            }
        }
    }

}
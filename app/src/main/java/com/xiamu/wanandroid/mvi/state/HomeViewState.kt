package com.xiamu.wanandroid.mvi.state

import com.xiamu.wanandroid.mvvm.model.entry.ArticleList

//对UI State的集中管理
data class HomeViewState(
    val fetchStatus: FetchStatus = FetchStatus.NotFetched,  //请求状态
    val article: ArticleList ?= null   //数据
    ){
}

//事件
sealed class HomeViewEvent{
    data class ShowToast(val message: String?): HomeViewEvent()
//    object ShowLoadingDialog : HomeViewEvent()
//    object DismissLoadingDialog : HomeViewEvent()
}

//行为
sealed class HomeViewAction(){
    object FetchNews: HomeViewAction()
    object OnSwipeRefresh: HomeViewAction()
}

sealed class FetchStatus {
    object Fetching : FetchStatus()
    object Fetched : FetchStatus()
    object NotFetched : FetchStatus()
    data class Error(val throwable: Throwable): FetchStatus()
}
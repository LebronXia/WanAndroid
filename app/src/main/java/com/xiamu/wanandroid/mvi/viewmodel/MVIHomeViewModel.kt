package com.xiamu.wanandroid.mvi.viewmodel

import androidx.lifecycle.viewModelScope
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvi.SharedFlowEvents
import com.xiamu.wanandroid.mvi.commonCatch
import com.xiamu.wanandroid.mvi.setEvent
import com.xiamu.wanandroid.mvi.setState
import com.xiamu.wanandroid.mvi.state.FetchStatus
import com.xiamu.wanandroid.mvi.state.HomeViewAction
import com.xiamu.wanandroid.mvi.state.HomeViewEvent
import com.xiamu.wanandroid.mvi.state.HomeViewState
import com.xiamu.wanandroid.mvvm.model.repository.MainHomeModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

class MVIHomeViewModel : BaseViewModel(){
    private val homeModel by lazy { MainHomeModel() }
    private var currentPage = 0

    private val _viewStates = MutableStateFlow(HomeViewState())
    val viewState = _viewStates.asStateFlow()
    private val _viewEvents = SharedFlowEvents<HomeViewEvent>()
    val viewEvents = _viewEvents.asSharedFlow()

    fun dispatch(viewAction: HomeViewAction){
        when(viewAction){
            HomeViewAction.FetchNews -> getHomeArticleList(false)
            HomeViewAction.OnSwipeRefresh -> getHomeArticleList(true)
        }
    }

    private fun getHomeArticleList(isRefresh: Boolean = false){
        viewModelScope.launch {
            if (isRefresh) currentPage = 0

            flow {
                val result = homeModel.getArticleList(currentPage)
                emit(result)

            }.onStart {
                _viewStates.setState {
                    copy(fetchStatus = FetchStatus.Fetching)
                }
            }.onEach {
                if (it is Result.Success){
                    val articleList = it.data
                    if (articleList!!.offset >= articleList.total){
                        return@onEach
                    }
                    currentPage++
                    _viewStates.setState {
                        copy(fetchStatus = FetchStatus.Fetched, article = it.data)
                    }
                } else if (it is Result.Error){
                    _viewStates.setState {
                        copy(fetchStatus = FetchStatus.NotFetched)
                    }
                    _viewEvents.setEvent(HomeViewEvent.ShowToast(it.exception.message))
                }

            }. commonCatch {
                _viewEvents.setEvent(HomeViewEvent.ShowToast("请求失败"))
            }.collect()
        }
    }


}
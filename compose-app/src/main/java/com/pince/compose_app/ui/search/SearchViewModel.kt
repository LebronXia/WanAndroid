package com.pince.compose_app.ui.search

import android.util.Log
import com.pince.compose_app.model.db.SearchHistoryBean
import com.pince.compose_app.model.db.SearchHistoryHelper
import com.pince.compose_app.model.repository.SearchRepo
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.baselibs.util.onFailure
import com.xiamu.baselibs.util.onSuccess
import com.xiamu.wanandroid.mvvm.model.entry.HotKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect

/**
 * Created by zxb in 2021/11/8
 */
class SearchViewModel : BaseViewModel(){

    private val searchRepo by lazy { SearchRepo() }

    private val _searchHotKeys = MutableStateFlow<List<HotKey>>(emptyList())
    val searchHotKeys = _searchHotKeys

    private val _searchHistory = MutableStateFlow<List<SearchHistoryBean>>(emptyList())
    val searchHistory = _searchHistory


    init {
        getSearchHotKey()
        getSearchHistory()
    }

    private fun getSearchHotKey(){
        launch {
            searchRepo.getSearchHotKey().onSuccess {
                _searchHotKeys.value = this
            }.onFailure {
                Log.e("xxx", "获取搜索热词 接口异常 ${it.message}")
            }
        }
    }

    private fun getSearchHistory(){
        launch {
            SearchHistoryHelper.getSearchHistoryAll().collect {
                _searchHistory.value = it
            }
        }
    }


}
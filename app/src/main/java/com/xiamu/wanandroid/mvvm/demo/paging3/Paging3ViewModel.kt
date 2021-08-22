package com.xiamu.wanandroid.mvvm.demo.paging3

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.entry.Article
import kotlinx.coroutines.flow.Flow

/**
 * Created by zxb in 2021/8/16
 */
class Paging3ViewModel: BaseViewModel() {

    fun getPagingData(): Flow<PagingData<Article>> {
        //cachedIn  用于将服务器返回的数据在viewModelScope这个作用域内进行缓存
        return Repository.getArticleList().cachedIn(viewModelScope)
    }
}
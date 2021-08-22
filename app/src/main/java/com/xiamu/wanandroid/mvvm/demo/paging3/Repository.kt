package com.xiamu.wanandroid.mvvm.demo.paging3

import androidx.paging.Config
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.xiamu.wanandroid.mvvm.model.api.WanRetrofitClient
import com.xiamu.wanandroid.mvvm.model.entry.Article
import com.xiamu.wanandroid.mvvm.model.repository.MainHomeModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by zxb in 2021/8/16
 */
object Repository {

    private const val PAGE_SIZE = 20

    fun getArticleList(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                    pageSize = PAGE_SIZE),   //每页所包含的数据量
            pagingSourceFactory = {CustomPageDataSource(WanRetrofitClient.service)}  //作为用于分页的数据源
        ).flow  //转成Flow类型
    }
}
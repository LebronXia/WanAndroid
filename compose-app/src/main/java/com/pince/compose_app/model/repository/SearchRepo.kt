package com.pince.compose_app.model.repository

import com.pince.compose_app.model.api.WanRetrofitClient
import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.mvvm.BaseModel
import com.xiamu.baselibs.http.Result
import com.xiamu.wanandroid.mvvm.model.entry.ArticleList
import com.xiamu.wanandroid.mvvm.model.entry.HotKey
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean

/**
 * Created by zhengxiaobo in 2019-11-14
 */
class SearchRepo : BaseModel(){

    suspend fun getSearchHotKey(): Result<List<HotKey>>{
        return safeApiCall(call = {requestData { WanRetrofitClient.service.getHotSearch()}})
    }

    suspend fun getSearchResult(page: Int, key: String): WanResponse<ArticleList>{
        return apiCall{WanRetrofitClient.service.queryBySearchKey(page, key)}
    }
}
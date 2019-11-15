package com.xiamu.wanandroid.mvvm.model.repository

import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.mvvm.BaseModel
import com.xiamu.wanandroid.mvvm.model.api.WanRetrofitClient
import com.xiamu.wanandroid.mvvm.model.entry.HotKey
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean

/**
 * Created by zhengxiaobo in 2019-11-14
 */
class SearchRepository : BaseModel(){

    suspend fun getSearchHotKey(): WanResponse<List<HotKey>>{
        return apiCall{ WanRetrofitClient.service.getHotSearch()}
    }
}
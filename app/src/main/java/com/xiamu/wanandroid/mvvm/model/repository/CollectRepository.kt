package com.xiamu.wanandroid.mvvm.model.repository

import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.mvvm.BaseModel
import com.xiamu.wanandroid.mvvm.model.api.WanRetrofitClient
import com.xiamu.wanandroid.mvvm.model.entry.ArticleList

/**
 * Created by zhengxiaobo in 2019-11-19
 */
class CollectRepository : BaseModel(){

    suspend fun getCollectList(page: Int): WanResponse<ArticleList>{
        return apiCall{WanRetrofitClient.service.getCollectList(page)}
    }


}
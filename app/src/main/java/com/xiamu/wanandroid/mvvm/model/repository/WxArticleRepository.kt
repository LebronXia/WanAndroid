package com.xiamu.wanandroid.mvvm.model.repository

import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.mvvm.BaseModel
import com.xiamu.wanandroid.mvvm.model.api.WanRetrofitClient
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean
import com.xiamu.wanandroid.mvvm.model.entry.TreeDetailBean

/**
 * Created by zhengxiaobo in 2019-11-09
 */
class WxArticleRepository : BaseModel(){

    suspend fun getWxArticles(): WanResponse<List<TreeBean>>{
        return apiCall{ WanRetrofitClient.service.getWxArtileList()}
    }

    suspend fun getWxArticleDetail(page: Int, id: Int): WanResponse<TreeDetailBean>{
        return apiCall { WanRetrofitClient.service.getWxArticleDetail(page, id) }
    }

}
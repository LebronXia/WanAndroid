package com.pince.compose_app.model.repository

import com.pince.compose_app.model.api.WanRetrofitClient
import com.xiamu.baselibs.mvvm.BaseModel
import com.xiamu.baselibs.http.Result
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean

/**
 * Created by zxb in 2021/11/9
 */
class WxArticleRepo : BaseModel(){

    /**
     * 获取公众号列表
     */
    suspend fun getWxArticle(): Result<List<TreeBean>>{
        return safeApiCall(call = {requestData { WanRetrofitClient.service.getWxArtileList()}})
    }

    /*
     *
     * 获取某个公众号历史文章列表
     */
    suspend fun getWxArticleList(page: Int, id: Int) = WanRetrofitClient.service.getWxArticleDetail(page, id)
}
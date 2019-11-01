package com.xiamu.wanandroid.mvvm.model.repository

import com.google.gson.Gson
import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.mvvm.BaseModel
import com.xiamu.wanandroid.mvvm.model.api.WanRetrofitClient
import com.xiamu.wanandroid.mvvm.model.entry.ArticleList
import com.xiamu.wanandroid.mvvm.model.entry.Banner
import com.xiamu.wanandroid.util.isSuccess
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-10-29
 */
class MainHomeModel : BaseModel() {

    suspend fun getArticleList(page: Int): Result<ArticleList>{
        return safeApiCall(call = {requestArticleList(page)}, errorMessage = "网络异常")
    }

    private suspend fun requestArticleList(page: Int): Result<ArticleList>{
        val response = WanRetrofitClient.service.getHomeArticles(page)
        return if (response.isSuccess()) Result.Success(response.data)
        else Result.Error(IOException(response.errMsg))
    }

    suspend fun getBanner(): WanResponse<List<Banner>>{
        return apiCall{WanRetrofitClient.service.getHomeBanner()}
    }

}
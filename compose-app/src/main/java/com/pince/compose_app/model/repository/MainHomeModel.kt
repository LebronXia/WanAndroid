package com.pince.compose_app.model.repository

import com.google.gson.Gson
import com.pince.compose_app.WanAndApplication
import com.pince.compose_app.extend.isSuccess
import com.pince.compose_app.model.api.WanRetrofitClient
import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.mvvm.BaseModel
import com.xiamu.baselibs.util.NetWorkUtils
import com.xiamu.wanandroid.mvvm.model.entry.ArticleList
import com.xiamu.wanandroid.mvvm.model.entry.Banner
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-10-29
 */
class MainHomeModel : BaseModel() {

    suspend fun getArticleList(page: Int = 0): Result<ArticleList>{
        if (NetWorkUtils.isNetworkAvailable(WanAndApplication.CONTEXT)){

        }
        return safeApiCall(call = {requestArticleList(page)}, errorMessage = "网络异常")
    }

    private suspend fun requestArticleList(page: Int): Result<ArticleList>{
        val response = WanRetrofitClient.service.getHomeArticles(page)
        return if (response.isSuccess()) Result.Success(response.data)
        else Result.Error(IOException(response.errorMsg))
    }

    suspend fun getBanner(): WanResponse<List<Banner>>{
        return apiCall{WanRetrofitClient.service.getHomeBanner()}
    }

}
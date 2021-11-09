package com.pince.compose_app.model.repository

import com.pince.compose_app.model.api.WanRetrofitClient
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.mvvm.BaseModel
import com.xiamu.wanandroid.mvvm.model.entry.BannerBean

/**
 * Created by zhengxiaobo in 2019-10-29
 */
class MainHomeRepo : BaseModel() {

    //首页列表
    suspend fun getHomeList(page: Int) = WanRetrofitClient.service.getHomeArticles(page)

//    suspend fun getArticleList(page: Int = 0): Result<CommonListPageModel<Article>>{
//        if (NetWorkUtils.isNetworkAvailable(WanAndApplication.CONTEXT)){
//
//        }
//        return safeApiCall(call = {requestArticleList(page)}, errorMessage = "网络异常")
//    }

//    private suspend fun requestArticleList(page: Int): Result<CommonListPageModel<Article>>{
//        val response = WanRetrofitClient.service.getHomeArticles(page)
//        return if (response.isSuccess()) Result.Success(response.data)
//        else Result.Error(IOException(response.errorMsg))
//    }

    suspend fun getBanner(): Result<List<BannerBean>>{
        return safeApiCall(call = {requestData{WanRetrofitClient.service.getHomeBanner()}})
    }


}
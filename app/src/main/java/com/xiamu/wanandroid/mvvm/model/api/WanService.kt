package com.xiamu.wanandroid.mvvm.model.api

import com.xiamu.baselibs.base.WanResponse
import com.xiamu.wanandroid.mvvm.model.entry.ArticleList
import com.xiamu.wanandroid.mvvm.model.entry.Banner
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by zhengxiaobo in 2019-10-29
 */
interface WanService {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    //获取首页文章列表
    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): WanResponse<ArticleList>

    //获取首页banner
    @GET("/banner/json")
    suspend fun getHomeBanner(): WanResponse<List<Banner>>
}
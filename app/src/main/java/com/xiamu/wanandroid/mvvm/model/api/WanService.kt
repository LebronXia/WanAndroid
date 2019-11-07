package com.xiamu.wanandroid.mvvm.model.api

import com.xiamu.baselibs.base.WanResponse
import com.xiamu.wanandroid.mvvm.model.entry.*
import retrofit2.http.*

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

    //登录
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") username:String, @Field("password")password:String): WanResponse<LoginBean>


    //注册
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(@Field("username") username: String, @Field("password")password: String, @Field("repassword")repassword:String): WanResponse<LoginBean>

    //体系数据
    @GET("/tree/json")
    suspend fun getTreeData(): WanResponse<List<TreeBean>>

    //知识体系下的文章
    @GET("/article/list/0/json?cid=60")
    suspend fun getTreeListData(@Path("cid")id: Int): WanResponse<TreeDetailBean>

}
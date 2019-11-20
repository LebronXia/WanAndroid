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
    @GET("/article/list/{page}/json")
    suspend fun getTreeListData(@Path("page") page: Int, @Query("cid")id: Int): WanResponse<TreeDetailBean>

    //获取公众号列表
    @GET("/wxarticle/chapters/json")
    suspend fun getWxArtileList(): WanResponse<List<TreeBean>>

    //查看某个公众号历史数据
    @GET("https://wanandroid.com/wxarticle/list/{page}/{id}/json")
    suspend fun getWxArticleDetail(@Path("page")page : Int, @Path("id") id: Int): WanResponse<TreeDetailBean>

    //项目
    @GET("/project/tree/json")
    suspend fun getProjectTree(): WanResponse<List<TreeBean>>

    //项目列表数据
    @GET("/project/list/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int, @Query("cid")id: Int): WanResponse<TreeDetailBean>

    //导航
    @GET("/navi/json")
    suspend fun getNaviData(): WanResponse<List<NaviBean>>

    //搜索热词
    @GET("/hotkey/json")
    suspend fun getHotSearch(): WanResponse<List<HotKey>>

    //搜索
    @POST("/article/query/{page}/json")
    @FormUrlEncoded
    suspend fun queryBySearchKey(@Path("page") page: Int, @Field("k") key: String): WanResponse<ArticleList>

    //收藏文章列表
    @GET("/lg/collect/list/{page}/json")
    suspend fun getCollectList(@Path("page")page: Int): WanResponse<ArticleList>

    //收藏站内文章
    @POST("/lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id")id: Int): WanResponse<Any>

    //文章列表中取消收藏
    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun unCollectArticle(@Path("id")id: Int): WanResponse<Any>

    //我的收藏页面取消收藏
    @POST("/lg/uncollect/{id}/json")
    suspend fun removeCollectArticle(@Path("id")id: Int): WanResponse<Any>
}
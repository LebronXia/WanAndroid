package com.xiamu.wanandroid.mvvm.model.repository

import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.mvvm.BaseModel
import com.xiamu.wanandroid.mvvm.model.api.WanRetrofitClient
import com.xiamu.wanandroid.mvvm.model.entry.ArticleList
import com.xiamu.wanandroid.mvvm.model.entry.Banner
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean
import com.xiamu.wanandroid.mvvm.model.entry.TreeDetailBean

/**
 * Created by zhengxiaobo in 2019-11-06
 */
class KnowTreeRepository : BaseModel(){

    suspend fun getTreeData(): WanResponse<List<TreeBean>>{
        return apiCall{ WanRetrofitClient.service.getTreeData()}
    }

    suspend fun getTreeListData(id:Int): WanResponse<TreeDetailBean>{
        return apiCall { WanRetrofitClient.service.getTreeListData(id) }
    }



}
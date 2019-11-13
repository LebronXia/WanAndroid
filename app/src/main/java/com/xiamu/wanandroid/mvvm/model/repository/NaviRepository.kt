package com.xiamu.wanandroid.mvvm.model.repository

import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.mvvm.BaseModel
import com.xiamu.wanandroid.mvvm.model.api.WanRetrofitClient
import com.xiamu.wanandroid.mvvm.model.entry.NaviBean
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean

/**
 * Created by zhengxiaobo in 2019-11-11
 */
class NaviRepository : BaseModel(){

    suspend fun getNaviData(): WanResponse<List<NaviBean>> {
        return apiCall{ WanRetrofitClient.service.getNaviData()}
    }

}
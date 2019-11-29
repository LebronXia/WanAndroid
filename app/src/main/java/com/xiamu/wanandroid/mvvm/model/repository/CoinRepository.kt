package com.xiamu.wanandroid.mvvm.model.repository

import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.mvvm.BaseModel
import com.xiamu.wanandroid.mvvm.model.api.WanRetrofitClient
import com.xiamu.wanandroid.mvvm.model.entry.CoinUserInfo

/**
 * Created by zhengxiaobo in 2019-11-22
 */
class CoinRepository: BaseModel(){

    suspend fun getCoinUserInfo(): WanResponse<CoinUserInfo>{
        return apiCall { WanRetrofitClient.service.getCoinUserInfo() }
    }
}
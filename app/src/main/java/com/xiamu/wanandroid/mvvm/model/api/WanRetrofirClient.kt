package com.xiamu.wanandroid.mvvm.model.api

import com.xiamu.baselibs.http.BaseRetrofitClient
import okhttp3.OkHttpClient

/**
 * Created by zhengxiaobo in 2019-10-29
 */
object WanRetrofirClient: BaseRetrofitClient(){

    //val service by lazy { getService() }
    override fun handleBuilder(builder: OkHttpClient.Builder) {
    }

}
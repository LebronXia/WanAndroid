package com.xiamu.baselibs.base

/**
 * Created by zhengxiaobo in 2019-10-29
 */
data class WanResponse<out T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T?
    ){
    companion object {
        const val SERVER_CODE_SUCCESS = 0 //接口请求响应业务处理 成功
    }
}
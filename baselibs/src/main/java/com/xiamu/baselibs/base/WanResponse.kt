package com.xiamu.baselibs.base

/**
 * Created by zhengxiaobo in 2019-10-29
 */
data class WanResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T?)
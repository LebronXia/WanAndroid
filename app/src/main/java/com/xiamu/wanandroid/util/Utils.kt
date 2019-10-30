package com.xiamu.wanandroid.util

import com.xiamu.baselibs.base.WanResponse

/**
 * Created by zhengxiaobo in 2019-10-30
 */
fun WanResponse<Any>.isSuccess(): Boolean = this.errorcode == 0
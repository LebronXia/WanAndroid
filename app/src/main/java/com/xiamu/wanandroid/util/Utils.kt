package com.xiamu.wanandroid.util

import android.app.Activity
import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.util.toast
import com.xiamu.wanandroid.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException

/**
 * Created by zhengxiaobo in 2019-10-30
 */
fun WanResponse<Any>.isSuccess(): Boolean = this.errorcode == 0

suspend fun executeResponse(response: WanResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
                            errorBlock: suspend CoroutineScope.() -> Unit) {
    coroutineScope {
        if (response.errorcode != 0) errorBlock()
        else successBlock()
    }
}

fun Activity.onNetError(e: Throwable, func: (e: Throwable) -> Unit) {
    if (e is HttpException) {
        toast(R.string.net_error)
        func(e)
    }
}
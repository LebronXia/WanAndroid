package com.xiamu.baselibs.util

import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.http.succeeded

/**
 * Created by zxb in 2021/11/4
 */

fun WanResponse<Any>.isSuccess(): Boolean = this.errorCode == WanResponse.SERVER_CODE_SUCCESS

inline fun <T: Any> Result<T>.onSuccess(action: T.() -> Unit): Result<T>{
    if (succeeded) action.invoke((this as Result.Success).data!!)
    return this
}

inline fun <T: Any> Result<T>.onFailure(action: (exception: Throwable) -> Unit): Result<T>{
    if (this is Result.Error) action.invoke((this as Result.Error).exception)
    return this
}
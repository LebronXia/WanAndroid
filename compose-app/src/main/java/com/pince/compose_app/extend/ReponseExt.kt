package com.pince.compose_app.extend

import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.http.Result
import kotlinx.coroutines.CoroutineScope
import java.lang.reflect.Method

/**
 * Created by zxb in 2021/11/4
 */


suspend fun <T: Any> (suspend() -> Result<T>).serverData(): Result<T>{
    var result: Result<T> = Result.Loading
    kotlin.runCatching {
        this.invoke()
    }.onFailure {
        result = Result.Error(RuntimeException(it))
    }.onSuccess {
        result = it
    }

    return result
}

package com.xiamu.baselibs.http


/**
 * Created by zhengxiaobo in 2019-10-30
 */
sealed class Result <out T : Any>{
    data class Success<out T: Any>(val data: T?) : Result<T>()

    data class Error(val exception: Exception): Result<Nothing>()

    //加载数据中
    object Loading: Result<Nothing>()

    override fun toString(): String {
        return when(this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Loading -> ""
        }
    }
}

/**
 * 返回结果如果是Success类，并且data非null才认为是成功的
 */
val Result<*>.succeeded: Boolean
    get() = this is Result.Success && data != null

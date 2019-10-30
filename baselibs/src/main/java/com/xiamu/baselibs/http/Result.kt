package com.xiamu.baselibs.http


/**
 * Created by zhengxiaobo in 2019-10-30
 */
//枚举类
sealed class Result <out T : Any>{
    data class Success<out T: Any>(val data: T) : Result<T>()

    data class Error(val exception: Exception): Result<Nothing>()

    override fun toString(): String {
        return when(this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }

}
package com.xiamu.baselibs.mvvm

import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.http.Result
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-10-28
 */
open class BaseModel: IModel{

   suspend fun <T: Any> apiCall(call: suspend () -> WanResponse<T>) : WanResponse<T>{
       return call.invoke()
   }

    //对异常进行处理
    suspend fun <T: Any> safeApiCall(call: suspend() -> Result<T>, errorMessage: String): Result<T>{
        return try {
            call()
        } catch (e: Exception){
            Result.Error(IOException(errorMessage, e))
        }
    }

    override fun onDestroy() {

    }
}
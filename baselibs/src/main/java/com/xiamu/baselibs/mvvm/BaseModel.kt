package com.xiamu.baselibs.mvvm

import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.util.isSuccess
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-10-28
 */
open class BaseModel: IModel{

   suspend fun <T: Any> apiCall(call: suspend () -> WanResponse<T>) : WanResponse<T>{
       return call.invoke()
   }

    //处理网络返回数据结果
    suspend fun <T: Any> safeApiCall(call: suspend() -> Result<T>, errorMessage: String = "网络异常"): Result<T>{
        return try {
            call()
        } catch (e: Exception){
            Result.Error(IOException(errorMessage, e))
        }
    }

    //接口成功，业务返回code处理
    suspend fun <T: Any> requestData(block: suspend() -> WanResponse<T>): Result<T>{
        val response = block.invoke()
        return if (response.isSuccess()) Result.Success(response.data)
        else Result.Error(IOException(response.errorMsg))
    }


    override fun onDestroy() {

    }
}
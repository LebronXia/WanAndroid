package com.xiamu.wanandroid.mvvm.model.repository

import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.mvvm.BaseModel
import com.xiamu.wanandroid.mvvm.model.api.WanRetrofitClient
import com.xiamu.wanandroid.mvvm.model.entry.LoginBean
import com.xiamu.wanandroid.util.isSuccess
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-11-05
 */
class LoginModel : BaseModel() {

    suspend fun login(username: String, password: String) : Result<LoginBean>{
        return safeApiCall(call = {requestLogin(username, password)},errorMessage = "登录失败")
    }

    suspend fun requestLogin(username:String, password:String): Result<LoginBean>{
        val response = WanRetrofitClient.service.login(username, password)
        return if (response.isSuccess()) Result.Success(response.data)
        else  Result.Error(IOException(response.errMsg))
    }

}
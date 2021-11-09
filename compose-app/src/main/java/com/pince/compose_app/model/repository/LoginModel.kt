package com.pince.compose_app.model.repository

import com.pince.compose_app.model.api.WanRetrofitClient
import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.mvvm.BaseModel
import com.xiamu.baselibs.util.isSuccess
import com.xiamu.wanandroid.mvvm.model.entry.LoginBean
import com.xiamu.wanandroid.mvvm.model.entry.RegisterBean
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-11-05
 */
class LoginModel : BaseModel() {

    suspend fun login(username: String, password: String) : Result<LoginBean>{
        return safeApiCall(call = {requestLogin(username, password)}, errorMessage = "登录失败")
    }

    suspend fun requestLogin(username:String, password:String): Result<LoginBean>{
        val response = WanRetrofitClient.service.login(username, password)
        return if (response.isSuccess()) Result.Success(response.data)
        else  Result.Error(IOException(response.errorMsg))
    }

    suspend fun register(username: String, password: String, repassword: String): Result<LoginBean>{
        return safeApiCall(call = {requestRegister(username, password, repassword)}, errorMessage = "注册失败")
    }

    suspend fun requestRegister(username:String, password:String, repassword: String): Result<LoginBean>{
        val response = WanRetrofitClient.service.register(username, password, repassword)
        return if (response.isSuccess())
            //requestLogin(username, password)
            Result.Success(response.data)
        else
            Result.Error(IOException(response.errorMsg))
    }

    suspend fun logot(): WanResponse<Any>{
        return apiCall{WanRetrofitClient.service.logot()}
    }

}
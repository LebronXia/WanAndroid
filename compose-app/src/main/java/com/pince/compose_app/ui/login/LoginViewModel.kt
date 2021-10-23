package com.pince.compose_app.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.pince.compose_app.helper.UserInfoManager
import com.pince.compose_app.model.UiModel
import com.pince.compose_app.model.repository.LoginModel
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.util.toast
import com.xiamu.wanandroid.mvvm.model.entry.LoginBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by zxb in 2021/10/19
 */
class LoginViewModel : ViewModel(){

    val loginScreenState = MutableStateFlow(UiModel<LoginBean>(false, null, null))
    private val loginModel by lazy { LoginModel() }

    fun autoLogin(){
        dispatchViewState(UiModel(true, null, null))
        viewModelScope.launch {
            delay(100)
            if (UserInfoManager.isAutoLogin()){
                dispatchViewState(UiModel(false,
                    null,
                    UserInfoManager.getUserInfo()))
            }
        }

    }

    fun login(username: String, password: String){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                loginModel.login(username, password)
            }
            if (result is Result.Success){
                UserInfoManager.onLogin(result.data!!)
                dispatchViewState(UiModel(false, null, result.data))
            } else if (result is Result.Error){
                dispatchViewState(UiModel(false, result.exception.message, null))
            }
        }
    }

    private fun dispatchViewState(uiModel: UiModel<LoginBean>){
        loginScreenState.value = uiModel
    }
}
package com.xiamu.wanandroid.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.xiamu.baselibs.http.Result
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.entry.ArticleList
import com.xiamu.wanandroid.mvvm.model.entry.LoginBean
import com.xiamu.wanandroid.mvvm.model.entry.RegisterBean
import com.xiamu.wanandroid.mvvm.model.repository.LoginModel
import com.xiamu.wanandroid.mvvm.model.repository.MainHomeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by zhengxiaobo in 2019-11-05
 */
class LoginViewModel : BaseViewModel() {

    private val loginModel by lazy { LoginModel() }
    var _uistate: MutableLiveData<UiModel<LoginBean>> = MutableLiveData()

    var _regUiState: MutableLiveData<UiModel<RegisterBean>> = MutableLiveData()

    fun login(username: String, password: String){

        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                emitUiState(true)
            }
            val result = loginModel.login(username, password)
            withContext(Dispatchers.Main){
                if (result is Result.Success){
                    emitUiState(showLoading = false, showSuccess = result.data)

                } else if (result is Result.Error){
                    emitUiState(showLoading = false, showError = result.exception.message)
                }
            }
        }

    }

    fun register(username: String, password: String, repassword: String){
        viewModelScope.launch (Dispatchers.Default){

            withContext(Dispatchers.Main){
                emitUiState(true)
            }
            val result = loginModel.register(username, password, repassword)
            withContext(Dispatchers.Main){
                if (result is Result.Success){
                    emitUiState(showLoading = false, showSuccess = result.data)
                } else if (result is Result.Error){
                    emitUiState(showLoading = false, showError = result.exception.message)
                }
            }

        }

    }
    private fun emitUiState(
        showLoading: Boolean = false,
        showError: String ?= null,
        showSuccess: LoginBean?= null
    ){
        val uiModel = UiModel(showLoading, showError, showSuccess)
        _uistate.value = uiModel
    }

//    private fun emitRegUiState(
//        showLoading: Boolean = false,
//        showError: String ?= null,
//        showSuccess: RegisterBean?= null
//    ){
//        val uiModel = UiModel(showLoading, showError, showSuccess)
//        _regUiState.value = uiModel
//    }
}
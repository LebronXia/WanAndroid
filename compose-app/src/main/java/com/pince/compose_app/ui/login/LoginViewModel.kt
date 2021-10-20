package com.pince.compose_app.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

/**
 * Created by zxb in 2021/10/19
 */
class LoginViewModel : ViewModel(){
    var username by mutableStateOf("")
    var password by mutableStateOf("")

    fun login(navController: NavHostController){
        viewModelScope.launch {

        }
    }
}
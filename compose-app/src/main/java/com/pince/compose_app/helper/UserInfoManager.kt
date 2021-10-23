package com.pince.compose_app.helper

import com.pince.compose_app.model.storage.AppPreferences
import com.xiamu.wanandroid.mvvm.model.entry.LoginBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by zxb in 2021/10/22
 */
object UserInfoManager {

    private var mUserInfo: LoginBean ? =null
    private var mAutoLogin : Boolean = false

    fun init(){
        GlobalScope.launch {
            withContext(Dispatchers.Main){
                mUserInfo = AppPreferences.getUser()
                if (isLogin()){
                    updateUserCoin()
                }
            }
        }
    }

    fun getUserInfo(): LoginBean?{
        return mUserInfo
    }

    fun isLogin(): Boolean{
        return mUserInfo != null
    }

    suspend fun isAutoLogin() : Boolean{
        return AppPreferences.getAutoLogin() ?: false
    }

    //更新金币
    private suspend fun updateUserCoin(){

    }

    fun onLogin(user: LoginBean){
        GlobalScope.launch {
            mUserInfo = user
            AppPreferences.setUser(user)
            AppPreferences.setAutoLogin(true)
            updateUserCoin()
        }

    }

    fun onLogout(){
        GlobalScope.launch {
            mUserInfo = null
            AppPreferences.setUser(null)
            AppPreferences.setAutoLogin(false)
        }
    }
}
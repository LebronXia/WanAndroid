package com.pince.compose_app.model.storage

import android.service.autofill.UserData
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pince.compose_app.WanAndApplication
import com.xiamu.wanandroid.mvvm.model.entry.LoginBean

/**
 * Created by zxb in 2021/10/22
 */
object AppPreferences {

    private val KEY_USER = stringPreferencesKey("user")
    private val KEY_CAN_AUTO_LOGIN = stringPreferencesKey("keyCanAutoLogin")

    private val dataStore by lazy {
        PreferencesDataStore(WanAndApplication.CONTEXT, WanAndApplication.CONTEXT.packageName + ".app")
    }

    suspend fun getUser(): LoginBean? {
        return dataStore.getModel(KEY_USER)
    }

    suspend fun setUser(user: LoginBean?){
        dataStore.putModel(KEY_USER, user)
    }

    suspend fun getAutoLogin(): Boolean? {
        return dataStore.getModel(KEY_CAN_AUTO_LOGIN)
    }

    suspend fun setAutoLogin(boolean: Boolean){
        dataStore.putModel(KEY_CAN_AUTO_LOGIN, boolean)
    }

}
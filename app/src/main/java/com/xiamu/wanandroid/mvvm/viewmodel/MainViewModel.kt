package com.xiamu.wanandroid.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.BaseViewHolder
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.entry.CoinUserInfo
import com.xiamu.wanandroid.mvvm.model.repository.CoinRepository
import com.xiamu.wanandroid.mvvm.model.repository.LoginModel
import com.xiamu.wanandroid.util.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-11-27
 */
class MainViewModel: BaseViewModel(){

    private val mCoinRepo by lazy { CoinRepository() }
    private val loginModel by lazy { LoginModel() }
    val coinUserState: MutableLiveData<CoinUserInfo> = MutableLiveData()
    val logotState: MutableLiveData<Boolean> = MutableLiveData()

    fun getCoinUserInfo(){
        launch {
            val result = withContext(Dispatchers.IO){ mCoinRepo.getCoinUserInfo() }
            executeResponse(result,{coinUserState.value = result.data}, {mException.value = IOException(result.errorMsg) })
        }
    }

    fun logot(){
        launch {
            val result = withContext(Dispatchers.IO){ loginModel.logot() }
            executeResponse(result,{logotState.value = true}, {mException.value = IOException(result.errorMsg) })
        }
    }

}
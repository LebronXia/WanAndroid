package com.xiamu.wanandroid.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiamu.baselibs.mvvm.BaseViewModel
import com.xiamu.wanandroid.mvvm.model.entry.NaviBean
import com.xiamu.wanandroid.mvvm.model.repository.NaviRepository
import com.xiamu.wanandroid.util.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Created by zhengxiaobo in 2019-11-11
 */
class NaviViewModel : BaseViewModel(){

    private val naviRepository by lazy { NaviRepository() }
    var naviState: MutableLiveData<List<NaviBean>> = MutableLiveData()

    fun getNaviData(){
        launch{
            val result = withContext(Dispatchers.IO){naviRepository.getNaviData()}
            executeResponse(result,{naviState.value = result.data}, {mException.value = IOException(result.errorMsg) })
        }
    }



}
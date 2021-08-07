package com.xiamu.wanandroid.mvvm.demo.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by zxb in 2021/8/7
 */
class ShareFlowViewModel: ViewModel() {

    private var job: Job ?= null

    fun startRefresh(){
        job = viewModelScope.launch(Dispatchers.IO) {
            while (true){
                LocalEventBus.postEvent(Event((System.currentTimeMillis())))
            }
        }
    }

    fun stopRefresh(){
        job?.cancel()
    }
}
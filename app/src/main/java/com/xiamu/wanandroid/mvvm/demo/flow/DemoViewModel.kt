package com.xiamu.wanandroid.mvvm.demo.flow

import com.xiamu.baselibs.mvvm.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by zxb in 2021/8/7
 */
class DemoViewModel: BaseViewModel() {

    private val _countState = MutableStateFlow(0)
    val countState: StateFlow<Int> = _countState

    fun incrementCount() {
        _countState.value++
    }

    fun decrementCount() {
        _countState.value--
    }
}
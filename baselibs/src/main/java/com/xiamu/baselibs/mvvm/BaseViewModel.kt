package com.xiamu.baselibs.mvvm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.simple.eventbus.EventBus

/**
 * Created by zhengxiaobo in 2019-10-28
 * 引入ViewModelScope来管理协程取消
 * viewModelScope 默认使用 Dispatchers.Main
 */
open class BaseViewModel : ViewModel(), IViewModel, LifecycleObserver {

    val mException: MutableLiveData<Throwable> = MutableLiveData()


    //suspend CoroutineScope.() -> Unit  指执行挂起函数
    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit){
        viewModelScope.launch { block() }

    }

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }

    //还没理解
    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Throwable) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    mException.value = e
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }

    data class UiModel<T: Any>(
        val showLoading: Boolean,
        val showError: String?,
        val showSuccess: T?
    )



}
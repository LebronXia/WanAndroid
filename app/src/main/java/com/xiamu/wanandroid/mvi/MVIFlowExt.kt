package com.xiamu.wanandroid.mvi

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.xiamu.baselibs.util.toast
import com.xiamu.wanandroid.app.WanAndApplication
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.reflect.KProperty1

//监听一个属性
fun<T, A> StateFlow<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,  //必须传递一个接收者，才可以获取属性值
    action: (A) -> Unit
){
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
            this@observeState.map {
                StateTuple1(prop1.get(it))
            }.distinctUntilChanged()  //反抖，值执行一次
                .collect {
                    (a) -> action.invoke(a)
                }
        }
    }
}

internal data class StateTuple1<A>(val a: A)

fun <T> MutableStateFlow<T>.setState(reducer: T.() -> T) {
    this.value = this.value.reducer()
}

suspend fun <T> SharedFlowEvents<T>.setEvent(vararg values: T){
    val eventList = values.toList()
    this.emit(eventList)
}

fun <T> SharedFlow<List<T>>.observeEvent(lifecycleOwner: LifecycleOwner, action: (T) -> Unit) {
    lifecycleOwner.lifecycleScope.launchWhenStarted {
        this@observeEvent.collect {
            it.forEach { event ->
                action.invoke(event)
            }
        }
    }
}

//类型别名
typealias SharedFlowEvents<T> = MutableSharedFlow<List<T>>

@Suppress("FunctionName")
fun <T> SharedFlowEvents(): SharedFlowEvents<T> {
    return MutableSharedFlow()
}

fun <T> Flow<T>.commonCatch(action: suspend FlowCollector<T>.(cause: Throwable) -> Unit): Flow<T>{
    return this.catch {
        if (it is UnknownHostException || it is SocketTimeoutException){
            toast(WanAndApplication.CONTEXT, "发生网络错误，请稍后重试")
        } else {
            toast(WanAndApplication.CONTEXT, "请求失败，请重试")
        }
        action(it)
    }
}


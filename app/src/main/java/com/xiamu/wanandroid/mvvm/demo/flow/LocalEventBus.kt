package com.xiamu.wanandroid.mvvm.demo.flow

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Created by zxb in 2021/8/7
 */
object LocalEventBus {

    //http://events.jianshu.io/p/039af595084a    StateFlow和SharedFlow

    //它可以将已发送过的数据发送给新的订阅者，并且具有高的配置性。
//    private val localEvents = MutableSharedFlow<Int>(
//        5,   //新的订阅者Collect时，发送几个已经发送过的数据给它
//        3,   //减去replay，MutableSharedFlow还缓存多少数据
//        BufferOverflow.DROP_LATEST //缓存策略，三种 丢掉最新值、丢掉最旧值和挂起
//    )

    private val localEvents = MutableSharedFlow<Event>()
    val events = localEvents.asSharedFlow()

    suspend fun postEvent(event: Event){
        localEvents.emit(event)
    }
}

data class Event(
    val timestamp: Long
)
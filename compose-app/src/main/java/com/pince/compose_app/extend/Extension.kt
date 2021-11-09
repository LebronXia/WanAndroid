package com.pince.compose_app.extend

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.util.toast
import kotlinx.coroutines.*
import org.simple.eventbus.EventBus
import retrofit2.HttpException

/**
 * Created by zxb in 2021/10/20
 */

suspend fun executeResponse(response: WanResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
                            errorBlock: suspend CoroutineScope.() -> Unit) {
    coroutineScope {
        when {
            response.errorCode == -1001 ->{
               // EventBus.getDefault().post(TokenEvent(true))
            }
            response.errorCode != 0 -> {
                errorBlock()
            }

            else -> successBlock()
        }

    }
}

fun Activity.onNetError(e: Throwable, func: (e: Throwable) -> Unit) {
    if (e is HttpException) {
        toast("网络错误")
        func(e)
    }
}

fun NavController.navigateWithBack(screen: String){
    popBackStack()
    navigate(screen)
}

fun NavController.navigationSingTop(screen: String){
    popBackStack()
    navigate(screen){
        launchSingleTop = true
    }
}

fun ViewModel.sleepTime(mills: Long = 1500, block: () -> Unit){
    viewModelScope.launch(Dispatchers.IO) {
        delay(mills)
        block
    }
}
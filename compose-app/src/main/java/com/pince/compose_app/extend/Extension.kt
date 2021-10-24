package com.pince.compose_app.extend

import android.app.Activity
import androidx.navigation.NavController
import com.xiamu.baselibs.base.WanResponse
import com.xiamu.baselibs.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import org.simple.eventbus.EventBus
import retrofit2.HttpException

/**
 * Created by zxb in 2021/10/20
 */

fun WanResponse<Any>.isSuccess(): Boolean = this.errorCode == 0

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
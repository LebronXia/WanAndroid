package com.xiamu.wanandroid.util.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.xiamu.wanandroid.constant.AppConstant
import com.xiamu.wanandroid.mvvm.model.event.NetworkChangeEvent
import com.xiamu.wanandroid.util.NetWorkUtils
import com.xiamu.wanandroid.util.Preference
import org.simple.eventbus.EventBus

/**
 * Created by zhengxiaobo in 2020-01-03
 */
class NetworkChangeReceiver : BroadcastReceiver(){

    /**
     * 缓存上一次的网络状态
     */
    private var hasNetwork: Boolean by Preference(AppConstant.HAS_NETWORK_KEY, true)

    override fun onReceive(context: Context, intent: Intent?) {
        val isConnected = NetWorkUtils.isNetworkAvailable(context)
        if (isConnected) {
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(NetworkChangeEvent(isConnected))
            }
        } else {
            EventBus.getDefault().post(NetworkChangeEvent(isConnected))
        }

    }
}
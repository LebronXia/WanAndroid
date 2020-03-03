package com.xiamu.baselibs.util

import android.content.Context
import android.net.ConnectivityManager

class NetWorkUtils {

    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val manager = context.applicationContext.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return !(null == info || !info.isAvailable)
        }

//        /**
//         * 检查是否有可用网络
//         */
//        fun isNetworkConnected(): Boolean {
//            val connectivityManager =
//                WanAndroidApp.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            return connectivityManager.activeNetworkInfo != null
//        }
    }
}
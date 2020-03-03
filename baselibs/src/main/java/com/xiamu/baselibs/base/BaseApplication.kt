package com.xiamu.baselibs.base

import android.content.Context
import androidx.multidex.MultiDexApplication
import kotlin.properties.Delegates

/**
 * Created by zhengxiaobo in 2020-01-05
 */
open class BaseApplication : MultiDexApplication() {

    companion object{
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
    }
}
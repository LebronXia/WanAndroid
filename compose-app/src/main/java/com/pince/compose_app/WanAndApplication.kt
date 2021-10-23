package com.pince.compose_app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.kingja.loadsir.core.LoadSir
import com.orhanobut.logger.Logger
import com.pince.compose_app.helper.UserInfoManager
import com.xiamu.baselibs.base.BaseApplication
import com.xiamu.baselibs.widget.loadsir.EmptyCallback
import com.xiamu.baselibs.widget.loadsir.LoadingCallback
import com.xiamu.baselibs.widget.loadsir.PlaceholderCallback
import com.xiamu.baselibs.widget.loadsir.RetryCallback
import kotlin.properties.Delegates


/**
 * Created by zhengxiaobo in 2019-10-30
 */
class WanAndApplication: BaseApplication() {

    companion object{
        private val TAG = "App"
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = this
        UserInfoManager.init()
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        initLoadSir()
    }


    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(EmptyCallback())
            .addCallback( LoadingCallback())
            .addCallback( PlaceholderCallback())
            .addCallback( RetryCallback())
            .setDefaultCallback(LoadingCallback::class.java)//设置默认状态页
            .commit()
    }

    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d(TAG, "onCreated: " + activity.componentName.className)

        }

        override fun onActivityStarted(activity: Activity) {
            Log.d(TAG, "onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.d(TAG, "onDestroy: " + activity.componentName.className)
        }
    }
}
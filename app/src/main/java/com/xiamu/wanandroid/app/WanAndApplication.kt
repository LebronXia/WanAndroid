package com.xiamu.wanandroid.app

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.hss01248.dialog.MyActyManager
import com.hss01248.dialog.StyledDialog
import kotlin.properties.Delegates



/**
 * Created by zhengxiaobo in 2019-10-30
 */
class WanAndApplication: Application() {

    companion object{
        private val TAG = "App"
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        //初始化全局dialog
        StyledDialog.init(CONTEXT)
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
    }

    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d(TAG, "onCreated: " + activity.componentName.className)
           // StyledDialog
            MyActyManager.getInstance().currentActivity = activity
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
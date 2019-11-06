package com.xiamu.wanandroid.app

import android.app.Application
import android.content.Context
import com.hss01248.dialog.StyledDialog
import kotlin.properties.Delegates



/**
 * Created by zhengxiaobo in 2019-10-30
 */
class WanAndApplication: Application() {

    companion object{
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        //初始化全局dialog
        StyledDialog.init(CONTEXT)
    }
}
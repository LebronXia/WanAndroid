package com.pince.compose_app.util

import android.widget.Toast
import com.pince.compose_app.WanAndApplication
import com.xiamu.baselibs.util.toast

/**
 * Created by zxb in 2021/10/22
 */
fun showToast(msg: Any?){
    Toast.makeText(WanAndApplication.CONTEXT, msg.toString(), Toast.LENGTH_SHORT).show()
}

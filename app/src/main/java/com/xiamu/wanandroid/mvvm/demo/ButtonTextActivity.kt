package com.xiamu.wanandroid.mvvm.demo

import androidx.lifecycle.lifecycleScope
import com.xiamu.baselibs.base.BaseActivity
import com.xiamu.wanandroid.R
import kotlinx.android.synthetic.main.activity_button_text.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.jessyan.autosize.utils.LogUtils

/**
 * Created by zxb in 2021/11/9
 */
class ButtonTextActivity: BaseActivity() {

    override fun useLoadSir(): Boolean = false

    override fun getLayoutResId(): Int = R.layout.activity_button_text

    override fun initView() {
        lifecycleScope.launch {
            val num = loadDataA()
            loadDataB(num)
        }
    }

    override fun initData() {

        haha1.setOnClickListener {
            LogUtils.d("haha1")
        }

        haha2.setOnClickListener {
            LogUtils.d("haha2")
        }

        haha3.setOnClickListener {
            LogUtils.d("haha3")
        }

        haha4.setOnClickListener {
            LogUtils.d("haha4")
        }
    }

    private suspend fun loadDataA():Int {
        withContext(Dispatchers.IO) {
            delay(3000)
        }
        return 1
    }

    private suspend fun loadDataB(num:Int) {
        withContext(Dispatchers.IO) {
            delay(1000)
        }
    }

}
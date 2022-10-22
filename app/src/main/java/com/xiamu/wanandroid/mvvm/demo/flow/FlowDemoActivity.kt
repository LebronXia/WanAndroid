package com.xiamu.wanandroid.mvvm.demo.flow

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.xiamu.baselibs.base.BaseModelActivity
import com.xiamu.wanandroid.R
import com.xiamu.wanandroid.mvvm.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by zxb in 2021/7/12
 */
class FlowDemoActivity : BaseModelActivity<DemoViewModel>(){

    override fun useLoadSir(): Boolean = false

    override fun providerVMClass(): Class<DemoViewModel>? = DemoViewModel::class.java
    override fun getLayoutResId(): Int  = R.layout.activity_flow
    override fun initView() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){


            }
        }


        lifecycleScope.launchWhenStarted {
            mViewModel.countState.flowWithLifecycle(lifecycle).distinctUntilChanged()

            mViewModel.countState.map {

            }.collect {

            }
        }

        lifecycleScope.launchWhenStarted {
            LocalEventBus.events.collect {

            }
        }
    }


    private fun count(): Flow<Int> = flow {
        var x = 0
        while (true) {
            if (x > 20) {
                break
            }
            emit(x)
            x = x.plus(1)
        }
    }


    @SuppressLint("LogNotTimber")
    override fun initData() {

        //适用于复杂的业务操作
        GlobalScope.launch(Dispatchers.Main) {
            count()
                .flowOn(Dispatchers.Unconfined)   //flowOn 只会更改上游数据流的 CoroutineContext
                .map {
                    Log.d("Coroutine", "map on ${Thread.currentThread().name}")
                    "this is $it" }
                .flowOn(Dispatchers.IO)
                .catch { ex ->
                     Log.d("Coroutine", "catch on ${Thread.currentThread().name}")
                    ex.printStackTrace()
                    Log.d("Coroutine", ex.toString())
                    emit("-1") }
                .collect {
                    Log.d("Coroutine", "catch on ${Thread.currentThread().name}")
                     Log.d("Coroutine", it)
            }
        }
    }
}
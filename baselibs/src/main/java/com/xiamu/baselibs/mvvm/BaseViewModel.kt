package com.xiamu.baselibs.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import org.simple.eventbus.EventBus

/**
 * Created by zhengxiaobo in 2019-10-28
 */
class BaseViewModel<M: IModel>(application: Application) : AndroidViewModel(application), IViewModel, LifecycleObserver {

    lateinit  var mModel : M

    constructor(application: Application, model: M) : this(application) {
        this.mModel = model
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }





}
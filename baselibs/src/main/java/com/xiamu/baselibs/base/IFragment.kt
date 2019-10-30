package com.xiamu.baselibs.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by zhengxiaobo in 2019-10-28
 */
interface IFragment {
   /*
    *
    * @param  val LayoutInflater :inflater? = null
    * @param  val ViewGroup :container? = null
    * @param  val Bundle :savedInstanceState? = null
    * @ return View
    */
   fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    /**
     * 数据初始化
     *
     * @param savedInstanceState Bundle
     */
     fun initData(savedInstanceState: Bundle?)

    /**
     * Activity 与 Fragment 通信接口
     * 此方法是让外部调用使 Fragment 做一些操作的,比如说外部的 Fragment 想让 Fragment 对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传 [android.os.Message],通过what字段,来区分不同的方法,
     * 在此方法中就可以 switch 做不同的操作,这样就可以用统一的入口方法做不同的事
     *
     *
     * 新姿势：可以通过 Activity 的 ViewModel 共享数据给包含的 Fragment，配合 LiveData 好用到爆。
     *
     * @param data Object
     * @see [Sharing Data Between Fragments](https://developer.android.com/topic/libraries/architecture/viewmodel.html.sharing_data_between_fragments)
     */
     fun setData(data: Any)
}
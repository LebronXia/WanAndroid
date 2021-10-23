package com.xiamu.wanandroid.mvvm.model.entry

/**
 * Created by zhengxiaobo in 2019-11-15
 */
data class SearchHistoryBean(
    val key: String = ""
){
    constructor(): this("")

    var id: Long = 0

}
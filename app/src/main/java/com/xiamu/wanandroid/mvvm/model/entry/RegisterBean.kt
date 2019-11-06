package com.xiamu.wanandroid.mvvm.model.entry

/**
 * Created by zhengxiaobo in 2019-11-06
 */
data class RegisterBean(
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val password: String,
    val type: Int,
    val username: String)
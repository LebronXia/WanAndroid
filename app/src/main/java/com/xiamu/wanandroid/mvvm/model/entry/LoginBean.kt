package com.xiamu.wanandroid.mvvm.model.entry

/**
 * Created by zhengxiaobo in 2019-11-05
 */
data class LoginBean(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val collectIds: List<Any>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)
package com.xiamu.wanandroid.mvvm.model.entry

/**
 * Created by zhengxiaobo in 2019-11-01
 */
data class BannerBean(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)
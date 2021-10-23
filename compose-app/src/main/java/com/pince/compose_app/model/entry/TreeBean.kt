package com.xiamu.wanandroid.mvvm.model.entry

import java.io.Serializable

/**
 * Created by zhengxiaobo in 2019-11-06
 *
 * */
data class TreeBean(
    val children: List<TreeBean>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Serializable

package com.xiamu.wanandroid.mvvm.demo.paging3

/**
 * Created by zxb in 2021/8/19
 */
interface DifferData {
    fun areItemsTheSame(data: DifferData): Boolean {
        return this == data
    }

    fun areContentsTheSame(data: DifferData): Boolean {
        return this == data
    }

    fun getChangePayload(data: DifferData): Any? {
        return null
    }
}
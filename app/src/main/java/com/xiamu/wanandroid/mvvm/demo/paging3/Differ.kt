package com.xiamu.wanandroid.mvvm.demo.paging3

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by zxb in 2021/8/19
 */
class  DifferCallback<T: DifferData>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? {
        return oldItem.getChangePayload(newItem)
    }
}
package com.xiamu.wanandroid.mvvm.view.demo.layoutmanager

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiamu.wanandroid.R

/**
 * Created by zxb in 2021/1/24
 */
class LayoutAdapter(mData: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_manager_view, mData){

    override fun convert(helper: BaseViewHolder, item: String?) {
       helper.setText(R.id.tv_text, item)
    }

}
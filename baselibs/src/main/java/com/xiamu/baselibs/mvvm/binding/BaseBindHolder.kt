package com.xiamu.baselibs.mvvm.binding

import android.view.View
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseViewHolder
import com.xiamu.baselibs.R

/**
 * Created by zhengxiaobo in 2019-10-31
 */
open class BaseBindHolder(view: View) : BaseViewHolder(view) {

    private var myView: View ?= view

    fun getBinding(): ViewDataBinding? {
        return myView?.getTag(R.id.BaseQuickAdapter_databinding_support) as ViewDataBinding?
    }

}
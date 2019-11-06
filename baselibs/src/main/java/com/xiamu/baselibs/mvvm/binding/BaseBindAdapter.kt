package com.xiamu.baselibs.mvvm.binding

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiamu.baselibs.R

/**
 * Created by zhengxiaobo in 2019-10-31
 */
abstract class BaseBindAdapter<T>(layoutResId: Int, data: List<T>?): BaseQuickAdapter<T, BaseBindHolder>(layoutResId, data){

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false)
                ?: return super.getItemView(layoutResId, parent)
            binding.root?.setTag(R.id.BaseQuickAdapter_databinding_support, binding)
        return binding.root
    }

}
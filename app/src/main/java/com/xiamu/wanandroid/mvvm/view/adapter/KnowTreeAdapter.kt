package com.xiamu.wanandroid.mvvm.view.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.xiamu.baselibs.mvvm.binding.BaseBindAdapter
import com.xiamu.baselibs.mvvm.binding.BaseBindHolder
import com.xiamu.wanandroid.BR
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean

/**
 * Created by zhengxiaobo in 2019-11-07
 */
class KnowTreeAdapter(layoutResId: Int, data: List<TreeBean>?): BaseBindAdapter<TreeBean>(layoutResId, data){

    override fun convert(helper: BaseBindHolder?, item: TreeBean?) {
        val binding = helper?.getBinding()
        binding?.setVariable(BR.treeBean, item)

        //它使数据绑定刷新所有挂起的更改。否则，它将视为另一个布局失效了
        binding?.executePendingBindings()
    }


}
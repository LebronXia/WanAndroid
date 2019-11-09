package com.xiamu.wanandroid.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.xiamu.wanandroid.mvvm.model.entry.TreeBean

/**
 * Created by zhengxiaobo in 2019-11-07
 */
object BindingUtil {

    @BindingAdapter("app:children")
    @JvmStatic fun bindChildren(view: TextView, children: List<TreeBean>){
        var des: StringBuffer = StringBuffer()
        children?.let {
            for (treebean : TreeBean in it){
                des.append(treebean.name + "  ")
            }
            view.setText(des)
        }
    }
}
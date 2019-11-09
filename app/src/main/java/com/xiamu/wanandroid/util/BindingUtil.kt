package com.xiamu.wanandroid.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
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

    @BindingAdapter("app:imageUrl")
    @JvmStatic fun loadImage(imageView: ImageView, url: String){
        ImageLoder.load(imageView.context, url, imageView);
    }


}
package com.xiamu.wanandroid.mvvm.view.adapter

import com.xiamu.baselibs.mvvm.binding.BaseBindAdapter
import com.xiamu.baselibs.mvvm.binding.BaseBindHolder
import com.xiamu.wanandroid.BR
import com.xiamu.wanandroid.mvvm.model.entry.Article

/**
 * Created by zhengxiaobo in 2019-10-31
 */
class HomeArticleAdapter(layoutResId: Int, data: List<Article>?): BaseBindAdapter<Article>(layoutResId, data) {

    override fun convert(helper: BaseBindHolder?, item: Article?) {
        val binding = helper?.getBinding()
        binding?.setVariable(BR.article, item)
        //它使数据绑定刷新所有挂起的更改。否则，它将视为另一个布局失效了
        binding?.executePendingBindings()

    }


}